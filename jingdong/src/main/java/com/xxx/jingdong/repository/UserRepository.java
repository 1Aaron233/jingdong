package com.xxx.jingdong.repository;

import com.xxx.jingdong.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author；Aaron
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
 User findByUsername(String username);

 User findByOpenid(String openid);

}
