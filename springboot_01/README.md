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
 
 
 然后直接运行即可,访问
 
 
 ## SpringBoot 热启动
 
 开发环境的调试
 
 热启动在正常开发项目中已经很常见了吧，虽然平时开发web项目过程中，改动项目启重启总是报错；但springBoot对调试支持很好，修改之后可以实时生效，需要添加以下的配置：
 
 * 第一步:在pom文件中添加 开发者工具依赖
 ```xml
<dependencies>

  <!-- https://mvnrepository.com/artifact/org.springframework/springloaded -->
  		<!--配置idea自动加载配置文件-->
  		<dependency>
  			<groupId>org.springframework</groupId>
  			<artifactId>springloaded</artifactId>
  		</dependency>
<!--添加依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <!-- optional=true,依赖不会传递，该项目依赖devtools；之后依赖myboot项目的项目如果想要使用devtools，需要重新引入 -->
    <optional>true</optional>
</dependency>

</dependencies>

<build>

    <plugins>

        <plugin>

            <groupId>org.springframework.boot</groupId>

            <artifactId>spring-boot-maven-plugin</artifactId>
<--project 中添加 spring-boot-maven-plugin,主要在eclipse中使用，idea中不需要添加此配置。-->
            <configuration>

                <fork>true</fork>

            </configuration>

        </plugin>

   </plugins>

</build>
```

该模块在完整的打包环境下运行的时候会被禁用。如果你使用java -jar启动应用或者用一个特定的classloader启动，它会认为这是一个“生产环境”

* 第二步: 更改idea配置

　　1） “File” -> “Settings” -> “Build,Execution,Deplyment” -> “Compiler”，选中打勾 “Build project automatically” 。

　　2） 组合键：“Shift+Ctrl+Alt+/” ，选择 “Registry” ，选中打勾 “compiler.automake.allow.when.app.running” 。

**接下来,再次运行项目,就会自动部署了**

> **注意:很多人做完这一步时,会没有出现想要的自动部署的结果**

解决办法: Build---Build Project或者 直接 ctrl+F9,如果弹出一个面板,可能你的自动部署就有问题,
只需要按照这个面板的错误提示,配置编译的版本就行了,然后再重启idea即可






如果浏览器有缓存,需要禁用

> Chrome禁用缓存
  
  　　F12或者“Ctrl+Shift+I”，打开开发者工具，“Network” 选项卡下 选中打勾 “Disable Cache(while DevTools is open)” 

 
 **idea最新版本也带有自动部署的插件,大家可以去网上搜索.但现在springboot已经为我们提供好了,是不是感觉很方便啊**
 
 ---
 ## 总结:
 
 使用spring boot可以非常方便、快速搭建项目，使我们不用关心框架之间的兼容性，适用版本等各种问题，我们想使用任何东西，仅仅添加一个配置就可以，所以使用sping boot非常适合构建微服务。
 
 
 
 
 补充:idea系统提示中文乱码问题解决: http://blog.csdn.net/wenniuwuren/article/details/46348105