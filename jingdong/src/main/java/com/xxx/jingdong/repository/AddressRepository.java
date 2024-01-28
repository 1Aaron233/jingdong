package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author；Aaron
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    //查询默认地址
    Address findByUserIdAndIsDefault(Integer userId, Integer isDefault);
    //根据用户查询所有地址
    List<Address> findAllByUserId(Integer UserId);
}
