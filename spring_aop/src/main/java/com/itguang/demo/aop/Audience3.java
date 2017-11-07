package com.itguang.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 使用环绕通知的Audience切面
 *
 * @author itguang
 * @create 2017-11-06 17:03
 **/
//@Aspect
public class Audience3 {

//    @Pointcut("execution(* com.itguang.demo.aop.Performance.perform(..))")
//    public void performance(){
//    }
//
//    @Around("performance()")
//    public void watchPerformance(ProceedingJoinPoint jp){
//        try {
//            System.out.println("Silence Cell Phone");
//            System.out.println("Taking seats");
//            //将控制权交给被通知的方法,即调用被通知的方法
//            jp.proceed();
//            System.out.println("pa pa pa");
//        }catch (Throwable e){
//            System.out.println("Demanding a refund");
//        }
//    }

}
