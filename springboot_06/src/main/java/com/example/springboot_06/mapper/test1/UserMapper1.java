package com.example.springboot_06.mapper.test1;


import com.example.springboot_06.entity.UserEntity;

import java.util.List;

public interface UserMapper1 {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
