package com.example.springboot_06.mapper.test2;


import com.example.springboot_06.entity.UserEntity;

import java.util.List;

public interface UserMapper2 {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
