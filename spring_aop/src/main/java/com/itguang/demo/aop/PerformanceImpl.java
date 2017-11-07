package com.itguang.demo.aop;

import org.springframework.stereotype.Component;

/**
 * @author itguang
 * @create 2017-11-07 8:26
 **/
//@Component
public class PerformanceImpl implements Performance{

    @Override
    public void perform() {
        System.out.println("表演单口相声...");
    }
}
