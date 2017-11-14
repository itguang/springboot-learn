package com.itguang.springbootthymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author itguang
 * @create 2017-11-14 10:59
 **/
@Controller
public class HelloController {


    @RequestMapping("/hello")
    public String hello(Model model,@RequestParam(value = "name",defaultValue = "default-sb") String name){
        System.out.println("name="+name);
        model.addAttribute("name",name);
        return "hello";

    }

    public String test(){

        return "";
    }

}
