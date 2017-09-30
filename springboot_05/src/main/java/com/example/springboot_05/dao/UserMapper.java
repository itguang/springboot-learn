package com.example.springboot_05.dao;

import com.example.springboot_05.entity.UserEntity;

import java.util.List;


//对应配置及文件 UserMapper.xml
public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
