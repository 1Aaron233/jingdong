package com.xxx.jingdong.service.impl;

import com.xxx.jingdong.enums.DefaultEnum;
import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.pojo.Address;
import com.xxx.jingdong.repository.AddressRepository;
import com.xxx.jingdong.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author；Aaron
 */
/*
此类可在买家端以及平台方后台进行操作
* **/
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository repository;

    @Override
    public Address add(Integer userId, Address address) {
        address.setUserId(userId);
        return repository.save(address);
    }

    //修改地址信息
    @Override
    public Address saveAddress(Integer userId, Address address) {
        //验证地址与用户
        checkAddress(userId,address);
        //更新地址信息
        return repository.save(address);
    }

    //设置默认地址（改默认地址）
    @Override
    @Transactional
    public Address updateDefault (Integer userId,Address address){
        //验证地址与用户
        checkAddress(userId,address);
        //如果默认地址存在，则把默认地址取消默认并且把此条信息设为默认
        Address exists=repository.findByUserIdAndIsDefault(userId, DefaultEnum.DEFAULT_YES.getCode());
        if (exists != null) {
            exists.setIsDefault(DefaultEnum.DEFAULT_NO.getCode());
            repository.save(exists);
        }
        address.setIsDefault(DefaultEnum.DEFAULT_YES.getCode());
        log.info("更新默认地址 result={}",address);
        return repository.save(address);
    }

    //获取该买家账号设定的默认地址信息（查默认地址）
    @Override
    public Address getDefault(Integer userId) {
        Address address= repository.findByUserIdAndIsDefault(userId,DefaultEnum.DEFAULT_YES.getCode());
        return address;
    }

    //查找所有买家账号下的地址（查）
    @Override
    public List<Address> getAll(Integer userId) {
        return repository.findAllByUserId(userId);
    }

    //验证地址是否存在以及用户是否一致
    private void checkAddress(Integer userId,Address address){
        Address result=repository.findById(address.getId()).orElse(null);
        //验证地址是否存在
        if (result == null) {
           throw new SellException(ResultEnum.ADDRESS_EXISIT);
        }
        //验证用户是否一致
        if (result.getUserId() != userId) {
            throw new SellException(ResultEnum.ADDRESS_USER_ERROR);
        }
    }

}
