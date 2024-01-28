package com.xxx.jingdong.service;

import com.xxx.jingdong.pojo.SellerInfo;

/**
 * Author；Aaron
 */
public interface SellerService {
    //通过openid获取卖家信息
    SellerInfo findSellerInfoByOpenid(String openid);

    SellerInfo findByUsernameAndPassword(String username,String password);

}
