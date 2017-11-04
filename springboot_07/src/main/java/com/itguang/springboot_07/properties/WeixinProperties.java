package com.itguang.springboot_07.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 收集配置文件的weixin 前缀属性
 *
 * @author itguang
 * @create 2017-11-04 10:47
 **/
@Component
//注入带 weixin 前缀的属性
@ConfigurationProperties(prefix = "weixin")
public class WeixinProperties {


    private String appid;

    //提供getter 和 setter方法
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
