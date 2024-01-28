package com.xxx.jingdong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xxx.jingdong.mybatis.mapper")
public class JingdongApplication {

    public static void main(String[] args) {
        SpringApplication.run(JingdongApplication.class, args);
    }

}
