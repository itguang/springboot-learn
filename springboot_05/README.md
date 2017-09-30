# spring boot(六)：如何优雅的使用mybatis

这两天启动了一个新项目因为项目组成员一直都使用的是mybatis，虽然个人比较喜欢jpa这种极简的模式，但是为了项目保持统一性技术选型还是定了 mybatis。到网上找了一下关于spring boot和mybatis组合的相关资料，各种各样的形式都有，看的人心累，结合了mybatis的官方demo和文档终于找到了最简的两种模式，花了一天时间总结后分享出来。

orm框架的本质是简化编程中操作数据库的编码，发展到现在基本上就剩两家了，一个是宣称可以不用写一句SQL的hibernate，一个是可以灵活调试动态sql的mybatis,两者各有特点，在企业级系统开发中可以根据需求灵活使用。发现一个有趣的现象：传统企业大都喜欢使用hibernate,互联网行业通常使用mybatis。

hibernate特点就是所有的sql都用Java代码来生成，不用跳出程序去写（看）sql，有着编程的完整性，发展到最顶端就是spring data jpa这种模式了，基本上根据方法名就可以生成对应的sql了.

mybatis初期使用比较麻烦，需要各种配置文件、实体类、dao层映射关联、还有一大推其它配置。当然mybatis也发现了这种弊端，初期开发了generator可以根据表结果自动生产实体类、配置文件和dao层代码，可以减轻一部分开发量；后期也进行了大量的优化可以使用注解了，自动管理dao层和配置文件等，发展到最顶端就是今天要讲的这种模式了，mybatis-spring-boot-starter就是springboot+mybatis可以完全注解不用配置文件，也可以简单配置轻松上手。


## mybatis-spring-boot-starter

官方说明：MyBatis Spring-Boot-Starter will help you use MyBatis with Spring Boot
其实就是myBatis看spring boot这么火热也开发出一套解决方案来凑凑热闹,但这一凑确实解决了很多问题，使用起来确实顺畅了许多。mybatis-spring-boot-starter主要有两种解决方案，一种是使用注解解决一切问题，一种是简化后的老传统。

```xml
 <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
```
这是我用 springboot Initlization 创建时勾选的,可以看出目前最新版本为 1.3.1


好了下来分别介绍两种开发模式


## 无配置文件注解版

就是一切使用注解搞定

**application.properties 添加相关配置**

```properties
# mysql配置

# 其实这个hibernate.hbm2ddl.auto参数的作用主要用于：自动创建|更新|验证数据库表结构,有四个值：

spring.datasource.url=jdbc:mysql://192.168.129.131:3306/springboot_04
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.properties.hibernate.hbm2ddl.auto=update
# 主要是指定生成表名的存储引擎为InneoDB
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
# 是否打印出自动生产的SQL，方便调试的时候查看
spring.jpa.show-sql= true
```

springboot会自动加载application.datasource.*相关配置，数据源就会自动注入到sqlSessionFactory中，sqlSessionFactory会自动注入到Mapper中，对了你一切都不用管了，直接拿起来使用就行了。


**在启动类中添加对mapper包扫描@MapperScan**

```java
@SpringBootApplication
@MapperScan("com.example.springboot_05.mapper")
public class Springboot05Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot05Application.class, args);
	}
}

```
或者直接在Mapper类上面添加注解@Mapper,建议使用上面那种，不然每个mapper加个注解也挺麻烦的

**编写entity:(封装结果集)**

```java
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String passWord;
    private UserSexEnum userSex;
    private String nickName;

    public UserEntity() {
        super();
    }

    public UserEntity(String userName, String passWord, UserSexEnum userSex) {
        super();
        this.passWord = passWord;
        this.userName = userName;
        this.userSex = userSex;
    }

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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public UserSexEnum getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSexEnum userSex) {
        this.userSex = userSex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "userName " + this.userName + ", pasword " + this.passWord + "sex " + userSex.name();
    }
}

```






**开发Mapper**

```java
public interface UserMapper {

    @Select("select * from users")
    //@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
    @Results({
            @Result(property = "userSex", column = "userSex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nickName")
    })
    List<UserEntity> getAll();


    @Select("select * from users where id =#{id}")
    @Results({
            @Result(property = "userSex", column = "userSex", javaType = UserSexEnum.class),
    })
    User getUserById(Long id);


    @Insert("insert into users (userName,passWord,userSex,nickName)values(#{userName},#{passWord},#{userSex},#{nickName})")
    void insertUser(UserEntity userEntity);

    @Update("UPDATE users SET userName=#{userName},nickName=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);


}
```

**测试**

```java
@Autowired
	public UserMapper userMapper;
	@Test
	public void testInsert(){

		userMapper.insertUser(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userMapper.insertUser(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userMapper.insertUser(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

	}
	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = userMapper.getAll();
		System.out.println(users.toString());
	}

	@Test
	public void testUpdate() throws Exception {
		UserEntity user =  userMapper.getUserById(5L);
		System.out.println(user.toString());
		user.setNickName("itguang");
		userMapper.update(user);

	}
```


## 极简xml版本

极简xml版本保持映射文件的老传统，优化主要体现在不需要实现dao的是实现层，系统会自动根据方法名在映射文件中找对应的sql.


**1、application.properties文件配置**

```properties
# mybatis 指定了mybatis基础配置文件和实体类映射文件的地址
mybatis.config-locations=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
```
指定了mybatis基础配置文件和实体类映射文件的地址

**2,新建mybatis配置文件, mybatis-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```
**3,新建dao层接口:  UserMapper.java**

```java


//对应配置及文件 UserMapper.xml
public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
```

**4,新建对应dao的mapper文件: UserMapper.xml文件**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.neo.mapper.test1.User1Mapper">
    <resultMap id="BaseResultMap" type="com.neo.entity.UserEntity">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="userName" property="userName" jdbcType="VARCHAR"/>
        <result column="passWord" property="passWord" jdbcType="VARCHAR"/>
        <result column="user_sex" property="userSex" javaType="com.neo.enums.UserSexEnum"/>
        <result column="nick_name" property="nickName" jdbcType="VARCHAR"/>
    </resultMap>

    <sql id="Base_Column_List">
        id, userName, passWord, userSex, nickName
    </sql>

    <select id="getAll" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM users
    </select>

    <select id="getOne" parameterType="java.lang.Long" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM users
        WHERE id = #{id}
    </select>

    <insert id="insert" parameterType="com.neo.entity.UserEntity">
        INSERT INTO
        users
        (userName,passWord,userSex)
        VALUES
        (#{userName}, #{passWord}, #{userSex})
    </insert>

    <update id="update" parameterType="com.neo.entity.UserEntity">
        UPDATE
        users
        SET
        <if test="userName != null">userName = #{userName},</if>
        <if test="passWord != null">passWord = #{passWord},</if>
        nickName = #{nickName}
        WHERE
        id = #{id}
    </update>

    <delete id="delete" parameterType="java.lang.Long">
        DELETE FROM
        users
        WHERE
        id =#{id}
    </delete>


</mapper>
```

**接下里就可以测试啦**




# 如何选择

两种模式各有特点，注解版适合简单快速的模式，其实像现在流行的这种微服务模式，一个微服务就会对应一个自已的数据库，多表连接查询的需求会大大的降低，会越来越适合这种模式。

老传统模式比适合大型项目，可以灵活的动态生成SQL，方便调整SQL，也有痛痛快快，洋洋洒洒的写SQL的感觉。

















