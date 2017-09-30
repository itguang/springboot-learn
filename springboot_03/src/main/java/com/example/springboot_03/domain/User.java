package com.example.springboot_03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //标识这是一个实体类
public class User {

    @Id
    @GeneratedValue //主键
    private Long id;

    @Column(nullable = false,unique = true)//不能为空且唯一
    private String userName;

    @Column(nullable = false)
    private String password;

    //默认可为空
    private  Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
