package com.example.springboot_02.config;


import com.example.springboot_02.filter.MyFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//添加此注解,标识这是一个配置类
public class WebConfiguration {

//    @Bean
//    public RemoteIpFilter remoteIpFilter() {
//        return new RemoteIpFilter();
//    }

    @Bean
    public FilterRegistrationBean testFilterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("paramName", "value");
        registrationBean.setName("MyFilter");
        registrationBean.setOrder(1);


        return registrationBean;
    }


}
