package com.xxx.jingdong.service;

import com.xxx.jingdong.form.UserForm;
import com.xxx.jingdong.form.UserVo;
import com.xxx.jingdong.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Author；Aaron
 */
public interface UserService {
    /**
     * 修改或保存数据
     * @param user
     * @return User
     */
    User save(User user);

    /**
     * 网页授权时用
     * @param openid
     * @return
     */
    User findByOpenid(String openid);

    /**
     * 根据id查找数据
     * @param id
     * @return User
     */
    User findOne(Integer id);

    /**
     * 分页查找数据
     * @param pageable
     * @return Page<User>
     */
    Page<User> findAll(Pageable pageable);

    /**
     * 注册用户
     * @param userForm
     * @return User
     */
    User userRegister(UserForm userForm);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    User checkLogin(String username, String password);

    /**
     * 更新用户
     * @param id
     * @param userVo
     * @return
     */
    User saveUser(Integer id, UserVo userVo);

}
