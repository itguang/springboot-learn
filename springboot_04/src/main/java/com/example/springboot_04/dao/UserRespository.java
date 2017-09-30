package com.example.springboot_04.dao;

import com.example.springboot_04.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRespository extends JpaRepository<User, Long> {

    //自定义简单查询

    //也使用一些加一些关键字And、 Or
    User findByUserNameOrAge(String userName, int age);

    //修改、删除、统计也是类似语法
    Long deleteById(Long id);

    Long countByUserName(String userName);


    //基本上SQL体系中的关键词都可以使用，例如：LIKE、 IgnoreCase、 OrderBy。
    List<User> findByUserNameLike(String likeName);

    User findByUserNameIgnoreCase(String userName);

    List<User> findByUserNameOrderByAgeDesc(String userName, int age);


    //自定义sql语句查询

    @Transactional
    @Modifying
    @Query("update User u set u.userName = ?1 where u.id = ?2")
    int modifyUserNameAndUserId(String userName, Long id);

    @Transactional
    @Modifying
    @Query("delete from User where id =?1")
    void deleteUserById(Long id);


}
