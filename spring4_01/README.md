# Spring01---装配bean

 ## 1,自动化装配bean
 
 spring从两个角度来实现自动化装配:
 
 * 组件扫描:(component scanning)
 * 自动装配:(autowiring)
 
 ### 第一步:创建可被发现的 bean
 
 ```java

@Component
public class SgtPeppers implements CompactDisc {
    public void play() {
        System.out.println("Playing.....");
    }
}
```

为了在 spring中阐述这个例子,我们首先创建了一个类 SgtPeppers() 实现了CompactDisc(CD播放器接口)

注意,我们使用了  `@Component` 注解.这个注解表名该类会作为组建类,并告知spring要为这个类创建bean

但是,组件扫描默认是不开启的,我们需要显示配置以下 Spring,从而命令它寻找带有 `@Component`
注解的类,并为其创建 bean.

### 第二步:配置 Spring期待用组件扫描

```java
@Configuration
@ComponentScan
public class CDPlayerConfig {

}
```
`@ComponentScan` 注解,将会使spring扫描这个包,以及这个包下的所有子包,
查找带有 `@Component` 注解的类.这样就能发现 `SgtPeppers` 并会在spring中为其创建一个bean.


### 测试:

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

    @Autowired
    private CompactDisc cd;

    @Test
    public void  cdShouldNotBeNull(){
        Assert.assertNotNull(cd);
    }

}
```
`@RunWith(SpringJUnit4ClassRunner.class)` 注解是为了方便在测试的时候自动创建 Spring的上下文,
`@ContextConfiguration(classes = CDPlayerConfig.class)` 注解钙素spring要在 `CDPlayerConfig.class` 类中加载配置.
因为 `CDPlayerConfig` 类中包含了 `@ComponentScan` 注解,因此应用上下文中就会包含 CompactDisc bean.


## 通过 java代码装配bean

### 第一步:创建javaConfig类
```java
@Configuration
public class CDPlayerConfig {

}
```
`@Configuration` 注解表明这个类是一个配置类,该类应该包含在spring应用程序的上下文中如何创建bean的细节.

现在我们把 `@ComponentScan` 组件扫描注解拿掉了,因为我们现在更加关注于显示配置.

移除了 `@ComponentScan` 注解,再次进行测试就会报错,会出现BeanCreationException 异常.

接下来我们看一下如何使用javaConfig来装配 CompactDisk .

### 声明简单的bean

要在javaConfig中声明 bean,我们需要编写一个方法,在这个方法中创建所需类型的示例,然后给这个方法添加 @Bean注解.

```java
@Configuration
public class CDPlayerConfig {
  
  @Bean
  public CompactDisc compactDisc() {
    return new SgtPeppers();
  }
  
  @Bean
  public CDPlayer cdPlayer(CompactDisc compactDisc) {
    return new CDPlayer(compactDisc);
  }

}
```
 @Bean 注解会告诉Spring 这个方法将会返回一个对象,改对象要注册为Spring应用程序上下文中的bean.方法体中包含了产生bean的逻辑.


## 通过 xml 装配bean(略) 


# 小结:

Spring框架的核心是Spring容器.容器负责管理应用中组件的声明周期,它会创建这些组件并保证它们的依赖会得到满足,这样的话,组件才会完成预定的任务.




 