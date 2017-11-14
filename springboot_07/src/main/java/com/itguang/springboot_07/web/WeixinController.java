package com.itguang.springboot_07.web;

/**
 * @author itguang
 * @create 2017-11-04 10:50
 **/

import com.itguang.springboot_07.properties.WeixinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeixinController {

    @Autowired
    private WeixinProperties weixinProperties;

    @RequestMapping(value = "test")
    public String test(){

      return   weixinProperties.getAppid();
    }
}
