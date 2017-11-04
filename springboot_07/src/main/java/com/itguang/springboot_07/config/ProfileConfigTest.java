package com.itguang.springboot_07.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 指定特定Profile的配置类
 *
 * @author itguang
 * @create 2017-11-04 11:23
 **/
@Configuration
@Profile("devlopment")
public class ProfileConfigTest {

}
