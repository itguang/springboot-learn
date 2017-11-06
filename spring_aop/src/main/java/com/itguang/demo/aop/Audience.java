package com.itguang.demo.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 观看演出的切面
 *
 * @author itguang
 * @create 2017-11-06 16:24
 **/
@Aspect
public class Audience {

    /**
     * 手机静音
     */
    @Before("execution(* com.itguang.demo.aop.Performance.perform(..))")
    public void  silenceCellPhone(){
        System.out.println("Silence Cell Phone");
    }

    /**
     * 就做
     */
    @Before("execution(* com.itguang.demo.aop.Performance.perform(..))")
    public void takeSeats(){
        System.out.println("Taking seats");
    }


    /**
     * 鼓掌
     */
    @AfterReturning("execution(* com.itguang.demo.aop.Performance.perform(..))")
    public void applause(){
        System.out.println("pa pa pa");
    }

    /**
     * 表演失败(异常),退款
     */
    @AfterThrowing("execution(* com.itguang.demo.aop.Performance.perform(..))")
    public void demandRefund(){
        System.out.println("Demanding a refund");
    }
}
