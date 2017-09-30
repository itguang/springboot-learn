package com.example.springboot_05;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springboot_05.mapper")
public class Springboot05Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot05Application.class, args);
	}
}
