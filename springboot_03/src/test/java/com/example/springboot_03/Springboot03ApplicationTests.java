package com.example.springboot_03;

import com.example.springboot_03.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

        stringRedisTemplate.opsForValue().set("test", "springboot");
        Assert.assertEquals("springboot", stringRedisTemplate.opsForValue().get("test"));

    }
//
	@Test
	public void testRedis(){

		User user = new User();
		user.setUserName("itguang");
		user.setAge(20);
		user.setPassword("123456");

		ValueOperations<String,User> operations = redisTemplate.opsForValue();
		operations.set("user",user);
		//设置超时 10秒
		operations.set("user2",user, 10,TimeUnit.SECONDS);

		Boolean exist = redisTemplate.hasKey("user");
		if (exist){
			System.out.println("===========true=============");
		}else {
			System.out.println("false");
		}
	}

}
