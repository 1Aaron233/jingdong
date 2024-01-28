package com.xxx.jingdong.utils;

import java.util.Random;

/**
 * Author；Aaron
 */
// 生成随机订单（同时随机字符串也可以用在验证码的时候使用）
// 与创建订单相关联，里面运用到了同步锁的修饰符，处理统一资源的时候会用到
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return
     * */
    public static synchronized String getUniquekey(){
        Random random =new Random();
        Integer number =random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
