package com.itguang.demo.aop;

import org.springframework.stereotype.Component;

/**
 * @author itguang
 * @create 2017-11-07 9:25
 **/
//@Component
public class PerformanceImpl2 implements Performance {


    @Override
    public void perform() {
        System.out.println("脱口秀");
    }
}
