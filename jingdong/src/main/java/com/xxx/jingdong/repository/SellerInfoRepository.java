package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author；Aaron
 */
@Repository
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findByOpenid(String openId);

    SellerInfo findBySellerId(String sellerId);

    SellerInfo findByUsernameAndPassword(String username,String password);
}
