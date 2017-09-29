# Spring boot 入门篇

## 什么是spring boot

Spring Boot是由Pivotal团队提供的全新框架，
其设计目的是用来简化新Spring应用的初始搭建以及开发过程。
该框架使用了特定的方式来进行配置，
从而使开发人员不再需要定义样板化的配置。用我的话来理解，
就是spring boot其实不是什么新的框架，
它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，
spring boot整合了所有的框架（不知道这样比喻是否合适）。


## 使用spring boot有什么好处

其实就是简单、快速、方便！平时如果我们需要搭建一个spring web项目的时候需要怎么做呢？

1）配置web.xml，加载spring和spring mvc

2）配置数据库连接、配置spring事务

3）配置加载配置文件的读取，开启注解

4）配置日志文件

...

配置完成之后部署tomcat 调试

...

现在非常流行微服务，如果我这个项目仅仅只是需要发送一个邮件，如果我的项目仅仅是生产一个积分；我都需要这样折腾一遍!

 

但是如果使用spring boot呢？

很简单，我仅仅只需要非常少的几个配置就可以迅速方便的搭建起来一套web项目或者是构建一个微服务！

 

使用sping boot到底有多爽，用下面这幅图来表达

 
 ## 快速入门
 
 使用idea的集成的 springboot initerlizer进行创建
 
 目录结构如下
 
 * src/main/java  程序开发以及主程序入口
 
 * src/main/resources 配置文件
 
 * src/test/java  测试程序
 
 
 ## 默认生成的Applection入口程序说明
 
 ```java

@SpringBootApplication  //启动dpringboot程序,而后自动扫描子包
public class Springboot01Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot01Application.class, args);
	}
}
```
 @SpringBootApplication 是一个组合注解,
 ```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    //...
}

```
其中一个作用就是:可以自动扫描所标识类当前所在包及其子包.

 
 
 ## 新建一个controller进行测试
 
 见:HelloController.java
 
 