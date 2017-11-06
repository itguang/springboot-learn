package com.itguang.demo.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP config
 *
 * @author itguang
 * @create 2017-11-06 16:53
 **/
@Configuration
@EnableAspectJAutoProxy
public class AOPConfig {

    @Bean
    public Audience2 audience2(){
        return new Audience2();
    }

}
