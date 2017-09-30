package com.example.springboot_03.controller;

import com.example.springboot_03.dao.UserRepository;
import com.example.springboot_03.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/name")
    @Cacheable(value = "getUser")//其中value的值就是缓存到redis中的key
    public User getUser() {

        User user = userRepository.findByUserName("itguang");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");

        return user;
    }
}
