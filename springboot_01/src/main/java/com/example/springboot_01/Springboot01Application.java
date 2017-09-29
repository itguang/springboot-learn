package com.example.springboot_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //启动dpringboot程序,而后自动扫描子包
public class Springboot01Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot01Application.class, args);

		System.out.println("springboot启动啦......");
	}
}
