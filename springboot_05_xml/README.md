# 极简xml版本

极简xml版本保持映射文件的老传统，优化主要体现在不需要实现dao的是实现层，系统会自动根据方法名在映射文件中找对应的sql.


## 1,新建 mapper-config.xml文件

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

## 2,新建Mapper.java和Mapper.xml文件

UserMapper.java

```java
public interface UserMapper {

    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);
}
```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.example.springboot_05_xml.mapper.UserMapper">

    <resultMap id="BaseRequestMap" type="com.example.springboot_05_xml.entity.UserEntity">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="userName" property="userName" jdbcType="VARCHAR" />
        <result column="passWord" property="passWord" jdbcType="VARCHAR" />
        <result column="userSex" property="userSex" javaType="com.example.springboot_05_xml.enums.UserSexEnum"/>
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

</mapper>
```






## 3、配置 application.properties文件

pom文件和上个版本一样，只是application.properties新增以下配置

```properties
# mybatis 指定了mybatis基础配置文件和实体类映射文件的地址
mybatis.config-locations=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
mybatis.type-aliases-package=com.example.springboot_05_xml.entity
```

## 在springboot的 Application启动类中添加Mapper扫描注解

**@MapperScan("com.example.springboot_05_xml.mapper")**

```java
@SpringBootApplication
@MapperScan("com.example.springboot_05_xml.mapper")
public class Springboot05XmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot05XmlApplication.class, args);
	}
}
```

