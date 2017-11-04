# Springboot 外置配置

> spring Boot 自动配置的bean提供了300多个用于微调的属性.当调整设置时,只需要在环境变量,Java系统属性,JNDI,命令行参数,属性文件进行配置 就好了.

## 举例:

假设我们想要禁止启动springboot应用时的 banner .有以下几种方法:

### 1.在属性文件中指定 application.properties
```
spring.main.show-banner=false
```
### 2.在 应用程序的命令行参数中指定

```
$ java -jar myapp.jar --spring.main.show-banner=false
```

### 3.还可以将属性设置为环境变量

如果你用的是bash或者zsh,可以使用 export 命令
```
$ export spring.main.show-banner=false
```

## 配置嵌入式服务器

从命令行运行springboot应用程序时,应用程序会启动一个嵌入式的服务器,监听8080端口.如果想要修改默认端口,
可以使用命令行参数

```
$ java -jar myapp.jar --server.port=80
```

## 配置日志

默认情况下,springboot会使用 Logback来记录日志,并用INFO级别输出到控制台.

**使用其他日志替换掉Logback**

一般来说,你不需要切换日志实现,Logback能很好满足你的需要.但是如果你决定要使用Log4j 或者Log4j2,

你只需要修改依赖:引入对应的日志实现的起步依赖,同时排除掉Logback 即可.

以maven为例:

**在maven里,可以用<exclusions> 来排除传递依赖**

```xml
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-log</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
```
然后加入Log4j的起步依赖即可

```xml
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-log4j</artifactId>
		</dependency>
```
**设置日志级别和日志输出文件位置:**

在applecation.properties 中以logging开头.

在 idea中会有很好的提示
![logging](images/s1.png)








