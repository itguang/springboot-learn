package com.example.springboot_05;

import com.example.springboot_05.entity.UserEntity;
import com.example.springboot_05.enums.UserSexEnum;
import com.example.springboot_05.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot05ApplicationTests {

	@Test
	public void contextLoads() {

	}

	@Autowired
	public UserMapper userMapper;
	@Test
	public void testInsert(){

		userMapper.insertUser(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userMapper.insertUser(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userMapper.insertUser(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

	}
	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = userMapper.getAll();
		System.out.println(users.toString());
	}

	@Test
	public void testUpdate() throws Exception {
		UserEntity user =  userMapper.getUserById(5L);
		System.out.println(user.toString());
		user.setNickName("itguang");
		userMapper.update(user);

	}

}
