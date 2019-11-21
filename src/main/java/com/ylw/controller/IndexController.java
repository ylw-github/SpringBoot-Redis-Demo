package com.ylw.controller;

import com.ylw.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class IndexController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/setString")
    public String setString(String key, String value) {
        redisService.set(key, value, 60l);
        return "success";
    }

    @RequestMapping("/getString")
    public String getString(String key) {
        return redisService.getString(key);
    }

    @RequestMapping("/setSet")
    public String setSet() {
        Set<String> set = new HashSet<String>();
        set.add("zhangsan");
        set.add("lisi");
        redisService.setSet("setTest", set);
        return "success";
    }
}
