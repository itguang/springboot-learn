package com.itguang.demo.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取自定义配置文件
 *
 * @author itguang
 * @create 2017-11-07 10:36
 **/
@Configuration
@ConfigurationProperties("test")
@PropertySource("classpath:test.properties")
public class TestBean {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
