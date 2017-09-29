package com.example.springboot_02.dao;

import com.example.springboot_02.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//对spring data jpa不熟的请私下去了解
public interface UserRepository extends JpaRepository<User,Long> {

    //也可以按照spring data jpa 的规则,自定义方法
    User findByUserName(String userName);

    User findByUserNameOrAge(String userName,int age);



}
