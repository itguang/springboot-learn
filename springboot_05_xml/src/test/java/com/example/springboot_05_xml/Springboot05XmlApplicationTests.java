package com.example.springboot_05_xml;

import com.example.springboot_05_xml.mapper.UserMapper;
import com.example.springboot_05_xml.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot05XmlApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	private UserMapper userMapper;

	@Test
	public void testGetALL(){
		List<UserEntity> all = userMapper.getAll();
	}



}
