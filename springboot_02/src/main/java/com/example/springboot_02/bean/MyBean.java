package com.example.springboot_02.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component //spring容器会扫描到此注解,把这个类放到spring容器中进行管理
public class MyBean {

    @Value("${com.itguang.msg}")
    private String msg;

    @Value("${com.itguang.name}")
    private String name;


}
