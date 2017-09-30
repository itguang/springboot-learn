# spring boot(三)：Spring Boot中Redis的使用

spring boot对常用的数据库支持外，对nosql 数据库也进行了封装自动化。

Redis是目前业界使用最广泛的内存数据存储。相比memcached，Redis支持更丰富的数据结构，例如hashes, lists, sets等，同时支持数据持久化。除此之外，Redis还提供一些类数据库的特性，比如事务，HA，主从库。可以说Redis兼具了缓存系统和数据库的一些特性，因此有着丰富的应用场景。本文介绍Redis在Spring Boot中两个典型的应用场景。


## 如何使用

1、引入 spring-boot-starter-redis

```xml
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
```

2、添加配置文件

```properties
# REDIS (RedisProperties)
# Redis数据库索引（默认为0）
spring.redis.database=0  
# Redis服务器地址
spring.redis.host=192.168.129.131
# Redis服务器连接端口
spring.redis.port=6379  
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8  
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1  
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=8  
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=0  
# 连接超时时间（毫秒）
spring.redis.timeout=0  
```
3、添加cache的配置类

```java
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60);//秒
        return rcm;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
```

3、好了，接下来就可以直接使用了

```java


@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

        stringRedisTemplate.opsForValue().set("test", "springboot");
        Assert.assertEquals("springboot", stringRedisTemplate.opsForValue().get("test"));

    }
//
	@Test
	public void testRedis(){

		User user = new User();
		user.setUserName("itguang");
		user.setAge(20);
		user.setPassword("123456");

		ValueOperations<String,User> operations = redisTemplate.opsForValue();
		operations.set("user",user);
		//设置超时 10秒
		operations.set("user2",user, 10,TimeUnit.SECONDS);

		Boolean exist = redisTemplate.hasKey("user");
		if (exist){
			System.out.println("===========true=============");
		}else {
			System.out.println("false");
		}


	}

}
```

以上都是手动使用的方式，如何在查找数据库的时候自动使用缓存呢，看下面；

4、自动根据方法生成缓存

```java
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/name")
    @Cacheable(value = "getUser")//其中value的值就是缓存到redis中的key
    public User getUser() {

        User user = userRepository.findByUserName("itguang");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");

        return user;
    }
}
```
其中value的值就是缓存到redis中的key






























