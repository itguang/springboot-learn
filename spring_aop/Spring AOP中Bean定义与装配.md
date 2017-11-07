# Spring AOP中bean的定义与装配

学习Spring AOP 时,做了个小demo,结果却出了点小问题:

去了 segmentfault 问了一下: https://segmentfault.com/q/1010000011886470

问题刚发布完,我突然就意识到了什么,于是乎,改了代码如下:

实现类 PerformanceImpl 改为

```java

/**
 * @author itguang
 * @create 2017-11-07 8:26
 **/
@Component
public class PerformanceImpl implements Performance{

    @Override
    public void perform() {
        System.out.println("表演单口相声...");
    }
}
```
只是加了个@Component注解,表示这是一个bean.

AOP配置类改为
```java
@Configuration
@EnableAspectJAutoProxy
public class AOPConfig {

    @Bean
    public Audience2 audience2(){
        return new Audience2();
    }



}

```
接下类就是测试类啦:
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAopApplicationTests {

	//bean 的名字默认是方法名,在 @Autowried注入的时候 名字不同调用的方法装配的bean也不同
	@Autowired
    	private Performance performance;

	@Test
	public void contextLoads() {
		performance.perform();
	}

}
```
这时候 performance名称可以随便取(但默认bean名称是类名称),因为此时我们只有一个Performance的实例.
```java
 @Autowired
private Performance performance;
```
       

运行测试结果:
```
Silence Cell Phone
Taking seats
表演单口相声...
pa pa pa
```

接下来修改代码如下:

添加一个实现类:PerformanceImpl2

```java
@Component
public class PerformanceImpl2 implements Performance {


    @Override
    public void perform() {
        System.out.println("脱口秀");
    }
}
```

这时候Spring已经管理了两个 Performance实例,因此测试类应该这样写

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAopApplicationTests {

	
	@Autowired
	private Performance performanceImpl;//@Component对应的类名称

	@Autowired
	private Performance performanceImpl2;//@Component对应的类名称

	@Test
	public void contextLoads() {
		performanceImpl.perform();
	}
	@Test
	public void Test2() {
		performanceImpl2.perform();
	}

}
```

如果我们去掉实现类上的 @Component,在配置类中使用 @bean注入,如:

```java
@Configuration
@EnableAspectJAutoProxy
public class AOPConfig {

    @Bean
    public Audience2 audience2(){
        return new Audience2();
    }

    //bean 的名字默认是方法名,在 @Autowried注入的时候 名字不同调用的方法装配的bean也不同
    @Bean
    public PerformanceImpl performance(){
        return new PerformanceImpl();
    }

    //bean 的名字默认是方法名,在 @Autowried注入的时候 名字不同调用的方法装配的bean也不同
    @Bean
    public PerformanceImpl2 performanceImpl2(){
        return new PerformanceImpl2();
    }

}

```

测试类中使用@AutoWried注入的bean名称要和配置类中产生Bean的方法名称一致哦:
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAopApplicationTests {


	@Autowired
	private Performance performance;

	@Autowired
	private Performance performanceImpl2;

	@Test
	public void contextLoads() {
		performance.perform();
	}
	@Test
	public void Test2() {
		performanceImpl2.perform();
	}

}
```







