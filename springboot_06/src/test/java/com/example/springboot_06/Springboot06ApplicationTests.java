package com.example.springboot_06;

import com.example.springboot_06.entity.UserEntity;
import com.example.springboot_06.mapper.test1.UserMapper1;
import com.example.springboot_06.mapper.test2.UserMapper2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot06ApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	UserMapper1 userMapper1;
	@Autowired
	UserMapper2 userMapper2;


	@Test
	public void test1(){
		UserEntity one = userMapper1.getOne(1L);
		System.out.println(one.toString());

	}

	@Test
	public void test2(){
		UserEntity one = userMapper2.getOne(1L);
		System.out.println(one.toString());

	}


}
