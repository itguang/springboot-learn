# spring boot(二)：web综合开发

## web开发
spring boot web开发非常的简单，其中包括常用的json输出、filters、property、log等

## json 接口开发

在以前的spring 开发的时候需要我们提供json接口的时候需要做那些配置呢

        添加 jackjson 等相关jar包
        配置spring controller扫描
        对接的方法添加@ResponseBody
        
就这样我们会经常由于配置错误，导致406错误等等，spring boot如何做呢，只需要类添加 @RestController 即可，默认类中的方法都会以json的格式返回

```java
@RestController
public class HelloWorldController {
    @RequestMapping("/getUser")
    public User getUser() {
        User user=new User();
        user.setUserName("小明");
        user.setPassWord("xxxx");
        return user;
    }
}
```

## 下面介绍springboot自定义Filter的使用


我们常常在项目中会使用filters用于录调用日志、排除有XSS威胁的字符、执行权限验证等等。Spring Boot自动添加了OrderedCharacterEncodingFilter和HiddenHttpMethodFilter，并且我们可以自定义Filter。

两个步骤：

        实现Filter接口，实现Filter方法
        添加@Configurationz 注解，将自定义Filter加入过滤链


代码如下:

自定义过滤器:MyFilter.java
```java

public class MyFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFliter.init()....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("This is my fliter:"+requestURL);

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        System.out.println("MyFilter.destory()....");

    }
}

```
创建配置类:WebConfiguration.java

```java
@Configuration//添加此注解,标识这是一个配置类
public class WebConfiguration {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistrationBean() {

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("paramName", "value");
        registrationBean.setName("MyFilter");
        registrationBean.setOrder(1);


        return registrationBean;
    }


}

```

启动,访问项目,控制台即可看到输出信息

```log
This is my fliter:http://192.168.40.120:8080/hello
This is my fliter:http://192.168.40.120:8080/favicon.ico
```







