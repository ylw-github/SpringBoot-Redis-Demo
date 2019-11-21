package com.ylw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object object, Long time) {
        // 存放String 类型
        if (object instanceof String) {
            setString(key, object);
        }
        // 存放 set类型
        if (object instanceof Set) {
            setSet(key, object);
        }
        // 设置有效期 以秒为单位
        stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    public void setString(String key, Object object) {
        // 如果是String 类型
        String value = (String) object;
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void setStringTransactoin(String key, Object object){
        stringRedisTemplate.setEnableTransactionSupport(true);
        // 开启事务
        stringRedisTemplate.multi();
        try {
            // 如果是String 类型
            String value = (String) object;
            stringRedisTemplate.opsForValue().set(key, value);
            //int i = 1/0;//如果解注释这一句，抛异常，会回滚到初始状态
        } catch (Exception e) {
            // 回滚
            stringRedisTemplate.discard();
        } finally {
            // 提交
            stringRedisTemplate.exec();
        }

    }
    public void setSet(String key, Object object) {
        Set<String> value = (Set<String>) object;
        for (String oj : value) {
            stringRedisTemplate.opsForSet().add(key, oj);
        }
    }

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
