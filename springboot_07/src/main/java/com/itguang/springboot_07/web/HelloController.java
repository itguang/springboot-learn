package com.itguang.springboot_07.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author itguang
 * @create 2017-11-14 9:50
 **/
@Controller
public class HelloController {


    @RequestMapping("hello")
    public String hello (Model model, @RequestParam(value = "name",required = false,defaultValue = "default_name")String name){
        model.addAttribute("name",name);
        System.out.println("name="+name);


        return "hello";
    }

}
