package com.example.springboot_05.mapper;

import com.example.springboot_05.entity.UserEntity;
import com.example.springboot_05.enums.UserSexEnum;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface UserMapper {

    @Select("select * from users")
    //@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
    @Results({
            @Result(property = "userSex", column = "userSex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nickName")
    })
    List<UserEntity> getAll();


    @Select("select * from users where id =#{id}")
    @Results({
            @Result(property = "userSex", column = "userSex", javaType = UserSexEnum.class),
    })
    UserEntity getUserById(Long id);


    @Insert("insert into users (userName,passWord,userSex,nickName)values(#{userName},#{passWord},#{userSex},#{nickName})")
    void insertUser(UserEntity userEntity);

    @Update("UPDATE users SET userName=#{userName},nickName=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);


}
