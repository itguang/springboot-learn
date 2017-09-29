package com.example.springboot_02;

import com.example.springboot_02.dao.UserRepository;
import com.example.springboot_02.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02ApplicationTests {

	@Test
	public void contextLoads() {


	}

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testUserRepository(){

		User user = new User();
		user.setUserName("itguang");
		user.setPassword("666666");
		user.setAge(22);
		userRepository.save(user);

	}

}
