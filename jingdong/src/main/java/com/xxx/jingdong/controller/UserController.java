package com.xxx.jingdong.controller;

import com.xxx.jingdong.domain.Result;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.form.UserForm;
import com.xxx.jingdong.form.UserVo;
import com.xxx.jingdong.pojo.User;
import com.xxx.jingdong.service.UserService;
import com.xxx.jingdong.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Author；Aaron
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param userForm
     * @param bindingResult
     * @return Result<User>
     */
    @PostMapping("/register")
    public Result<User> userRegister(@Valid UserForm userForm, BindingResult bindingResult){
        //效验form参数
        if (bindingResult.hasErrors()){
            log.error("[登陆注册]参数不正确，userForm={}",userForm);
            throw  new SellException(ResultEnum.USER_PARAMS_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //调用方法
        User user=userService.userRegister(userForm);
        //如果返回User为空，说明方法出错
        if(user == null){
            log.error("[登陆注册]参数不正确，userForm={}",userForm);
            throw  new SellException(ResultEnum.USER_REGISTER_ERROR);
        }
        return ResultUtil.success(user);
    }

    /**
     * 用户登录验证
     * @param username
     * @param password
     * @return Result<User>
     */
    @PostMapping("/login")
    public Result<User> checkLogin(@RequestParam("username") String username,
                                   @RequestParam("password") String password){
        //用户参数-用户名验证
        if(username == null || "".equals(username)){
            throw  new SellException(ResultEnum.USER_PARAMS_ERROR);
        }
        //用户参数-密码验证
        if(password == null || "".equals(password)){
            throw  new SellException(ResultEnum.USER_PARAMS_ERROR);
        }
        //登录验证
        User user =userService.checkLogin(username,password);
        //响应数据
        return ResultUtil.success(user);
    }

    /**
     * 更新用户
     * @param id
     * @param userVo
     * @return Result<User>
     */
    @PostMapping("/saveUser/{id}")
    public Result<User> saveUser(@PathVariable("id") Integer id, UserVo userVo){
        //用户参数验证
        if(id == null || id<=0){
            throw  new SellException(ResultEnum.USER_PARAMS_ERROR);
        }
        //用户参数验证
        if(userVo == null){
            throw  new SellException(ResultEnum.USER_PARAMS_ERROR);
        }
        //更改验证
        User user =userService.saveUser(id,userVo);
        //如果返回User为空，说明方法出错
        if(user == null){
            log.error("[登陆注册]用户更新失败",userVo.getUsername());
            throw  new SellException(ResultEnum.USER_REGISTER_ERROR);
        }
        //响应数据
        return ResultUtil.success(user);
    }
}
