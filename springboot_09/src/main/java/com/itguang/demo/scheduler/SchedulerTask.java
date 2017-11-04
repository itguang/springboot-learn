package com.itguang.demo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器
 *
 * @author itguang
 * @create 2017-11-04 14:12
 **/
@Component
public class SchedulerTask {
    private int count=0;

    //一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。
    //按顺序依次为:秒,分钟,小时,天,月,星期,年. 参;http://blog.csdn.net/prisonbreak_/article/details/49180307
    @Scheduled(cron="*/6 * * * * ?")
    public void process(){
        System.out.println("我来了"+(count++));
    }



}
