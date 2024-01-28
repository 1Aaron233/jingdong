package com.xxx.jingdong.service;

import com.xxx.jingdong.pojo.Address;

import java.util.List;

/**
 * Author；Aaron
 */
public interface AddressService {
    /**
     * 添加地址
     * @param userId
     * @param address
     * @return Address
     */
    Address add(Integer userId, Address address);

    /**
     * 更新地址
     * @param userId
     * @param address
     * @return Address
     */
    Address saveAddress(Integer userId, Address address);

    /**
     * 获取默认地址
     * @param userId
     * @return Address
     */
    Address getDefault(Integer userId);

    /**
     * 设置默认地址
     * @param userId
     * @param address
     * @return Address
     */
    Address updateDefault(Integer userId,Address address);

    /**
     * 获取所有地址
     * @param userId
     * @return List<Address>
     */
    List<Address> getAll(Integer userId);


}
