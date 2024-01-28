package com.xxx.jingdong.service.impl;


import com.xxx.jingdong.pojo.SellerInfo;
import com.xxx.jingdong.repository.SellerInfoRepository;
import com.xxx.jingdong.service.SellerService;
import com.xxx.jingdong.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author；Aaron
 */
/*
此类仅可在平台方后台进行操作
* **/
@Service
public class SellerServiceImpl implements SellerService {
    private final String SALT="yan";
    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findByUsernameAndPassword(String username, String password) {
        String passwordMd5= MD5Util.getMd5Password(password,SALT);
        return repository.findByUsernameAndPassword(username,passwordMd5);
    }
}
