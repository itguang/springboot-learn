package com.example.springboot_06;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.example.springboot_06.mapper")
public class Springboot06Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot06Application.class, args);
	}
}
