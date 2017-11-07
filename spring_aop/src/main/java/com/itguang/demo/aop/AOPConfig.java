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

    //bean 的名字默认是方法名,在 @Autowried注入的时候 名字不同调用的方法装配的bean也不同
    @Bean
    public PerformanceImpl performance(){
        return new PerformanceImpl();
    }

    //bean 的名字默认是方法名,在 @Autowried注入的时候 名字不同调用的方法装配的bean也不同
    @Bean
    public PerformanceImpl2 performanceImpl2(){
        return new PerformanceImpl2();
    }

}
