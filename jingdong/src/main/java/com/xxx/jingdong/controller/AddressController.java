package com.xxx.jingdong.controller;

import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.pojo.Address;
import com.xxx.jingdong.service.AddressService;
import com.xxx.jingdong.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author；Aaron
 */
@RestController
@RequestMapping("api/v1")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 添加地址
     * @param userId
     * @param address
     * @param bindingResult
     * @return Result<Address>
     */
    @PostMapping("/addAddress/{userId}")
    public Result<Address> addAddress( @PathVariable("userId") Integer userId,
                                       @Validated Address address,
                                      BindingResult bindingResult
                                      ){
        if (bindingResult.hasErrors()){
            throw new SellException(ResultEnum.USER_PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Address result= addressService.add(userId,address);
        if (result == null){
            throw new SellException(ResultEnum.ADDRESS_ADD_ERROR);
        }
        return ResultUtil.success(result);
    }

    /**
     * 更新地址
     * @param userId
     * @param address
     * @param bindingResult
     * @return Result<Address>
     */
    @PostMapping("/updateAddress/{userId}")
    public Result<Address> updateAddress(@PathVariable("userId") Integer userId,
                                         @Validated Address address,
                                         BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            throw new SellException(ResultEnum.USER_PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Address result= addressService.saveAddress(userId,address);
        if (result == null){
            throw new SellException(ResultEnum.ADDRESS_UPDATE_ERROR);
        }
        return ResultUtil.success(result);
    }

    /**
     * 获取默认地址
     * @param userId
     * @return Result<Address>
     */
    @GetMapping("/defaultAddress/{userId}")
    public Result<Address> getDefaultAddress(@PathVariable("userId") Integer userId){
        if (userId == null || userId <=0){
            throw new SellException(ResultEnum.ADDRESS_PARAMS_ERROR);
        }
        Address result=addressService.getDefault(userId);
        if (result == null){
            throw new SellException(ResultEnum.ADDRESS_EXISIT);
        }
        return ResultUtil.success(result);
    }

    /**
     * 设置默认地址
     * @param userId
     * @return Result<Address>
     */
    @PostMapping("/setDefAddress/{userId}")
    public Result<Address> setDefaultAddress(@PathVariable("userId") Integer userId,Address address){
        if (userId == null || userId <=0){
            throw new SellException(ResultEnum.ADDRESS_PARAMS_ERROR);
        }
        Address result=addressService.updateDefault(userId,address);
        if (result == null){
            throw new SellException(ResultEnum.ADDRESS_EXISIT);
        }
        return ResultUtil.success(result);
    }

    /**
     * 获取所有地址
     * @param userId
     * @return Result<List<Address>>
     */
    @GetMapping("/address/{userId}")
    public Result<List<Address>> getAddress( @PathVariable("userId") Integer userId){
        if (userId == null || userId<= 0){
            throw new SellException(ResultEnum.ADDRESS_PARAMS_ERROR);
        }
        List<Address> result=addressService.getAll(userId);
        if (result == null){
            throw new SellException(ResultEnum.ADDRESS_EXISIT);
        }
        return ResultUtil.success(result);
    }
}
