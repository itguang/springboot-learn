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

## application.properties配置文件中读取属性

application.properties是默认的配置文件,会自动加载

添加数据:

```
com.itguang.msg=itguang
com.itguang.name=小光光
```
那么接下来怎么读取呢?
其实现在配置文件已经被加载到spring容器中了,我们只需要像之前一样在bean中读取就行

创建一个Bean 叫MyBean.java

```java

@Component //spring容器会扫描到此注解,把这个类放到spring容器中进行管理
public class MyBean {

    @Value("${com.itguang.msg}")
    private String msg;

    @Value("${com.itguang.name}")
    private  String name;

}
```

## log配置

配置输出的地址和输出级别
```
logging.path=/user/local/log
logging.level.com.favorites=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=ERROR
```
path为本机的log地址，logging.level 后面可以根据包路径配置不同资源的log级别

## 数据库操作

在这里我重点讲述mysql、spring data jpa的使用，其中mysql 就不用说了大家很熟悉，jpa是利用Hibernate生成各种自动化的sql，如果只是简单的增删改查，基本上不用手写了，spring内部已经帮大家封装实现了。


### 1、添加相jar包
```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
     <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
```

### 2、添加配置文件
```
 spring.datasource.url=jdbc:mysql://localhost:3306/test
    spring.datasource.username=root
    spring.datasource.password=root
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    
    spring.jpa.properties.hibernate.hbm2ddl.auto=update
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
    spring.jpa.show-sql= true
```

其实这个hibernate.hbm2ddl.auto参数的作用主要用于：自动创建|更新|验证数据库表结构,有四个值：
        
        create： 每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
        create-drop ：每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
        update：最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。
        validate ：每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
        
        

dialect 主要是指定生成表名的存储引擎为InneoDB
show-sql 是否打印出自动生产的SQL，方便调试的时候查看


### 添加实体类和Dao

创建一个User.java

**Entity中不映射成列的字段得加@Transient 注解，不加注解也会映射成列**

```java
@Entity //标识这是一个实体类
public class User {

    @Id
    @GeneratedValue //主键
    private Long id;

    @Column(nullable = false,unique = true)//不能为空且唯一
    private String userName;

    @Column(nullable = false)
    private String password;

    //默认可为空
    private  Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```
创建dao层

这里使用spring data jpa

dao只要继承JpaRepository类就可以，几乎可以不用写方法，还有一个特别有尿性的功能非常赞，就是可以根据方法名来自动的生产SQL，比如findByUserName 会自动生产一个以 userName 为参数的查询方法，比如 findAlll 自动会查询表里面的所有数据，比如自动分页等等。。

```java
//对spring data jpa不熟的请私下去了解
public interface UserRepository extends JpaRepository<User,Long> {

    //也可以按照spring data jpa 的规则,自定义方法
    User findByUserName(String userName);

    User findByUserNameOrAge(String userName,int age);



}

```

编写单元测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02ApplicationTests {

	@Test
	public void contextLoads() {


	}

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testUserRepository(){

		User user = new User();
		user.setUserName("itguang");
		user.setPassword("666666");
		user.setAge(22);
		userRepository.save(user);

	}

}

```

在 testUserRepository 方法上右键运行此方法,如果配置没错即可看到 greenbar,
再到数据库中去看下,发现多了一张表和一条记录.


## thymeleaf模板
   Spring boot 推荐使用来代替jsp,thymeleaf模板到底是什么来头呢，让spring大哥来推荐，下面我们来聊聊
   
   Thymeleaf 介绍
   
   Thymeleaf是一款用于渲染XML/XHTML/HTML5内容的模板引擎。类似JSP，Velocity，FreeMaker等，它也可以轻易的与Spring MVC等Web框架进行集成作为Web应用的模板引擎。与其它模板引擎相比，Thymeleaf最大的特点是能够直接在浏览器中打开并正确显示模板页面，而不需要启动整个Web应用。
   
   好了，你们说了我们已经习惯使用了什么 velocity,FreMaker，beetle之类的模版，那么到底好在哪里呢？
   比一比吧
   Thymeleaf是与众不同的，因为它使用了自然的模板技术。这意味着Thymeleaf的模板语法并不会破坏文档的结构，模板依旧是有效的XML文档。模板还可以用作工作原型，Thymeleaf会在运行期替换掉静态值。Velocity与FreeMarker则是连续的文本处理器。
   下面的代码示例分别使用Velocity、FreeMarker与Thymeleaf打印出一条消息：
 ```html
 Velocity: <p>$message</p>
   FreeMarker: <p>${message}</p>
   Thymeleaf: <p th:text="${message}">Hello World!</p>
```
  
** 注意，由于Thymeleaf使用了XML DOM解析器，因此它并不适合于处理大规模的XML文件。**

### 页面即原型
  
  在Web开发过程中一个绕不开的话题就是前端工程师与后端工程师的写作，在传统Java Web开发过程中，前端工程师和后端工程师一样，也需要安装一套完整的开发环境，然后各类Java IDE中修改模板、静态资源文件，启动/重启/重新加载应用服务器，刷新页面查看最终效果。
  
  但实际上前端工程师的职责更多应该关注于页面本身而非后端，使用JSP，Velocity等传统的Java模板引擎很难做到这一点，因为它们必须在应用服务器中渲染完成后才能在浏览器中看到结果，而Thymeleaf从根本上颠覆了这一过程，通过属性进行模板渲染不会引入任何新的浏览器不能识别的标签，例如JSP中的，不会在Tag内部写表达式。整个页面直接作为HTML文件用浏览器打开，几乎就可以看到最终的效果，这大大解放了前端工程师的生产力，它们的最终交付物就是纯的HTML/CSS/JavaScript文件。

## WebJars

WebJars是一个很神奇的东西，可以让大家以jar包的形式来使用前端的各种框架、组件。

### 什么是WebJars

什么是WebJars？WebJars是将客户端（浏览器）资源（JavaScript，Css等）打成jar包文件，以对资源进行统一依赖管理。WebJars的jar包部署在Maven中央仓库上。

### 为什么使用

我们在开发Java web项目的时候会使用像Maven，Gradle等构建工具以实现对jar包版本依赖管理，以及项目的自动化管理，但是对于JavaScript，Css等前端资源包，我们只能采用拷贝到webapp下的方式，这样做就无法对这些资源进行依赖管理。那么WebJars就提供给我们这些前端资源的jar包形势，我们就可以进行依赖管理。















