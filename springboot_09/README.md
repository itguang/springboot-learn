# Spring Boot定时器

在我们的项目开发过程中，经常需要定时任务来帮助我们来做一些内容，springboot默认已经帮我们实行了，只需要添加相应的注解就可以实现

## 开启定时器

在启动类上面加上@EnableScheduling即可开启定时
    

```java

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

```

定时器的设置有两种:

### 1

```java
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
```

### 2

```java
@Component
public class ScheduleTash2 {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelay = 3000)
    public void printCurrentTime(){
        System.out.println("为您报时:"+dateFormat.format(new Date()));

    }




}

```


## 参数说明:

###  @Scheduled(cron="*/6 * * * * ?")

 @Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron="*/6 * * * * ?",一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。

具体参数说明,可以参考这篇文章: http://blog.csdn.net/prisonbreak_/article/details/49180307


###  @Scheduled(fixedDelay = 3000)

* @Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
* @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
* @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次



