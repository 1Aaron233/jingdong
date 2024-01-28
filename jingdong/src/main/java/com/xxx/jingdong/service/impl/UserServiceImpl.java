package com.xxx.jingdong.service.impl;


import com.xxx.jingdong.enums.ResultEnum;
import com.xxx.jingdong.exception.SellException;
import com.xxx.jingdong.form.UserForm;
import com.xxx.jingdong.form.UserVo;
import com.xxx.jingdong.pojo.User;
import com.xxx.jingdong.repository.UserRepository;
import com.xxx.jingdong.service.UserService;
import com.xxx.jingdong.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.UUID;

/**
 * Author；Aaron
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final String SALT="yan";
    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }

    @Override
    public User findOne(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    //注册处理
    @Override
    public User userRegister(UserForm userForm) {
        //密码确认
        if(!userForm.getPassword().equals(userForm.getRepassword())){
            log.error("[登陆注册]参数不正确，userForm={}",userForm);
            throw new SellException(ResultEnum.USER_PASSWORD_DISCORDANT);
        }
        //用户是否已注册
        User result =repository.findByUsername(userForm.getUsername());
        if(result!=null){
            log.error("[登录注册]该用户不存在",userForm.getUsername());
            throw new SellException(ResultEnum.USER_EXISIT_NO);
        }
        //保存登录信息
        User user=new User();
        user.setUsername(userForm.getUsername());
        //TODO 密码加盐加密后验证(★)
        user.setPassword(MD5Util.getMd5Password(userForm.getPassword(),SALT));
        //getNumbers 封装随机编号作为id
        user.setNumbers(getNumbers());
        user = repository.save(user);
        return user;
    }

    @Override
    public User checkLogin(String username, String password){
        //用户是否存在
        User user =repository.findByUsername(username);
        if(user == null){
            log.error("[登陆注册]该用户不存在",username);
            throw new SellException(ResultEnum.USER_EXISIT_NO);
        }
        //密码是否正确(表单密码加密后与数据库密码对比)
        //TODO 密码加盐加密后验证(★)
        String passwordMD5 =MD5Util.getMd5Password(password,SALT);
        if (!user.getPassword().equals(passwordMD5)){
            log.error("[登陆注册]用户密码{}不正确",password);
            throw new SellException(ResultEnum.USER_PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public User saveUser(Integer id, UserVo userVo){
        //用户是否存在
        User user=repository.findById(id).orElse(null);
        if (user==null){
            log.error("[登录注册]该用户不存在");
            throw new SellException(ResultEnum.USER_EXISIT_NO);
        }
        //是否是当前用户，防越权（越权判断）
        if (!user.getUsername().equals(userVo.getUsername())){
            log.error("【登录注册】用户请求不合法");
            throw new SellException(ResultEnum.USER_REQUEST_ERROR);
        }
        //保存用户信息
        BeanUtils.copyProperties(userVo,user);
        //TODO 密码与编码加密（加盐）处理(★)
        user.setPassword(MD5Util.getMd5Password(userVo.getPassword(),SALT));
        user.setNumbers(getNumbers());
        //传统md5
        //user.setPassword(DigestUtils.md5DigestAsHex(userVo.getPassword().getBytes()));
        //更新用户
        User result = repository.save(user);
        return result;
    }


    //设置用户ID编号（和key工具类其实可以等同的）
    private synchronized String getNumbers(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return String.valueOf(System.currentTimeMillis()).substring(0,6)+String.valueOf(number);
    }
}
