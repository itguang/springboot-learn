package com.itguang.demo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Spring定时器
 *
 * @author itguang
 * @create 2017-11-04 14:24
 **/
@Component
public class ScheduleTash2 {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelay = 3000)
    public void printCurrentTime(){
        System.out.println("为您报时:"+dateFormat.format(new Date()));

    }




}
