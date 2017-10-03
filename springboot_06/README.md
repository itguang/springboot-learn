# springboot(六)：springboot+mybatis多数据源最简解决方案


说起多数据源，一般都来解决那些问题呢，主从模式或者业务比较复杂需要连接不同的分库来支持业务。我们项目是后者的模式，网上找了很多，大都是根据jpa来做多数据源解决方案，要不就是老的spring多数据源解决方案，还有的是利用aop动态切换，感觉有点小复杂，其实我只是想找一个简单的多数据支持而已，折腾了两个小时整理出来，供大家参考。


## 1,配置文件

pom包就不贴了比较简单该依赖的就依赖，主要是数据库这边的配置：

```properties


# 指定mybatis的配置文件
mybatis.config-locations=classpath:mybatis/mybatis-config.xml

# datasource 1
spring.datasource.test1.driverClassName = com.mysql.jdbc.Driver
spring.datasource.test1.url = jdbc:mysql://192.168.129.131:3306/test1?useUnicode=true&characterEncoding=utf-8
spring.datasource.test1.username = root
spring.datasource.test1.password = root


## datasource 2
spring.datasource.test2.driverClassName = com.mysql.jdbc.Driver
spring.datasource.test2.url = jdbc:mysql://192.168.129.131:3306/test2?useUnicode=true&characterEncoding=utf-8
spring.datasource.test2.username = root
spring.datasource.test2.password = root
```
**一个test1库和一个test2库，其中test1位主库，在使用的过程中必须指定主库，不然会报错。**

## 2,数据源配置



   **1,创建 DataSource**

   **2,创建SqlSessionFaction**

   **3,创建事物管理**

   **4,包装到SqlSessionTemplate**

**DataSource1Config.java**

```java


/**
 * 数据源配置
 */

@Configuration
//配置 Mapper.java 扫描
@MapperScan(basePackages = "com.example.springboot_06.mapper.test1",sqlSessionTemplateRef = "test1SqlSessionTemplate")
public class DataSource1Config {



    //1,创建 DataSource
    @Bean(name = "test1DataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.dataSource.test1")//配置application.properties 文件的属性前缀
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }

    //2,创建SqlSessionFaction
    @Bean(name = "test1SqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFaction(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test1/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    //3,创建事物管理
    @Primary
    @Bean(name = "test1TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource") DataSource dataSource){

        return new DataSourceTransactionManager(dataSource);
    }


    //4,包装到SqlSessionTemplate
    @Bean(name = "test1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}

```
**DataSource2Config.java**

```java

@Configuration
//配置 Mapper.java 扫描
@MapperScan(basePackages = "com.example.springboot_06.mapper.test2",sqlSessionTemplateRef = "test2SqlSessionTemplate")
public class DataSource2Config {

    //1,创建 DataSource

    //2,创建SqlSessionFaction

    //3,创建事物管理

    //4,包装到SqlSessionTemplate



    //1,创建 DataSource
    @Bean(name = "test2DataSource")

    @ConfigurationProperties(prefix = "spring.dataSource.test2")//配置application.properties 文件的属性前缀
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }

    //2,创建SqlSessionFaction
    @Bean(name = "test2SqlSessionFactory")

    public SqlSessionFactory testSqlSessionFaction(@Qualifier("test2DataSource") DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/test2/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    //3,创建事物管理

    @Bean(name = "test2TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource){

        return new DataSourceTransactionManager(dataSource);
    }

    //4,包装到SqlSessionTemplate
    @Bean(name = "test2SqlSessionTemplate")

    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}

```

`@MapperScan(basePackages = "com.example.springboot_06.mapper.test2",sqlSessionTemplateRef = "test2SqlSessionTemplate")`

这块的注解就是指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate。所有@Bean都需要按照命名指定正确。


## 3,dao层和xml层(Mapper.java 和 Mapper.xml文件)

**UserMapper1.java**

```java

public interface UserMapper1 {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
```
**UserMapper.xml(位于resource下mybatis/mapper/test1 目录)**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.example.springboot_06.mapper.test1.UserMapper1">

    <resultMap id="BaseRequestMap" type="com.example.springboot_06.entity.UserEntity">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="userName" property="userName" jdbcType="VARCHAR" />
        <result column="passWord" property="passWord" jdbcType="VARCHAR" />
        <result column="userSex" property="userSex" javaType="com.example.springboot_06.enums.UserSexEnum"/>
        <result column="nickName" property="nickName" jdbcType="VARCHAR" />
    </resultMap>

    <sql id="Base_Column_List" >
        id, userName, passWord, userSex, nickName
    </sql>

    <select id="getAll" resultMap="BaseRequestMap"  >
        SELECT
        <include refid="Base_Column_List" />
        FROM users
    </select>

    <select id="getOne" parameterType="java.lang.Long" resultMap="BaseRequestMap" >
        SELECT
        <include refid="Base_Column_List" />
        FROM users
        WHERE id = #{id}
    </select>




</mapper>
```

**注意: <mapper namespace="com.example.springboot_06.mapper.test1.UserMapper1">**

Mapper.java和Mapper.xml不在同一目录下,名称可不同,因为是在DataSourceConfig.java中进行配置的

## 测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot06ApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	UserMapper1 userMapper1;
	@Autowired
	UserMapper2 userMapper2;


	@Test
	public void test1(){
		UserEntity one = userMapper1.getOne(1L);
		System.out.println(one.toString());

	}

	@Test
	public void test2(){
		UserEntity one = userMapper2.getOne(1L);
		System.out.println(one.toString());

	}


}

```

**至此,多数据源配置完成.**





