package com.example.springboot_05_xml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springboot_05_xml.mapper")
public class Springboot05XmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot05XmlApplication.class, args);
	}
}
