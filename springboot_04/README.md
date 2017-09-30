# spring boot(四)：spring data jpa的使用

> [Spring Data JPA 参考指南 中文版](https://www.gitbook.com/book/ityouknow/spring-data-jpa-reference-documentation/details)


> spring data jpa...买本书好好研究下

## 首先了解JPA是什么？
   
   JPA(Java Persistence API)是Sun官方提出的Java持久化规范。它为Java开发人员提供了一种对象/关联映射工具来管理Java应用中的关系数据。他的出现主要是为了简化现有的持久化开发工作和整合ORM技术，结束现在Hibernate，TopLink，JDO等ORM框架各自为营的局面。值得注意的是，JPA是在充分吸收了现有Hibernate，TopLink，JDO等ORM框架的基础上发展而来的，具有易于使用，伸缩性强等优点。从目前的开发社区的反应上看，JPA受到了极大的支持和赞扬，其中就包括了Spring与EJB3.0的开发团队。
   
   注意:JPA是一套规范，不是一套产品，那么像Hibernate,TopLink,JDO他们是一套产品，如果说这些产品实现了这个JPA规范，那么我们就可以叫他们为JPA的实现产品。
   
   Spring Data JPA 是 Spring 基于 ORM 框架、JPA 规范的基础上封装的一套JPA应用框架，可使开发者用极简的代码即可实现对数据的访问和操作。它提供了包括增删改查等在内的常用功能，且易于扩展！学习并使用 Spring Data JPA 可以极大提高开发效率！
   
   **spring data jpa让我们解脱了DAO层的操作，基本上所有CRUD都可以依赖于它来实现**
   

## 基本查询
   基本查询也分为两种，一种是spring data默认已经实现，一种是根据查询的方法来自动解析成SQL。
   
   
### 预先生成方法

spring data jpa 默认预先生成了一些基本的CURD的方法，例如：增、删、改等等

1 继承JpaRepository
```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```
再看 JpaRepository,继承了 PagingAndSortingRepository和 QueryByExampleExecutor

```java
@NoRepositoryBean
public interface JpaRepository<T, ID extends Serializable>
		extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {
            //......
            
            
       
    }
```

单元测试:
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot04ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    public UserRespository userRespository;

    @Test
    public void testRespository() {

        System.out.println("===count===="+ userRespository.count());

        List<User> users = userRespository.findAll();
        System.out.println(users);

        


    }

}

```

**自定义简单查询**

自定义的简单查询就是根据方法名来自动生成SQL，主要的语法是findXXBy,readAXXBy,queryXXBy,countXXBy, getXXBy后面跟属性名称：

    User findByUserName(String userName);

也使用一些加一些关键字And、 Or

    User findByUserNameOrEmail(String username, String email);
修改、删除、统计也是类似语法

    Long deleteById(Long id);

    Long countByUserName(String userName)
基本上SQL体系中的关键词都可以使用，例如：LIKE、 IgnoreCase、 OrderBy。

    List<User> findByEmailLike(String email);

    User findByUserNameIgnoreCase(String userName);
    
    List<User> findByUserNameOrderByEmailDesc(String email);


## 复杂查询

   在实际的开发中我们需要用到分页、删选、连表等查询的时候就需要特殊的方法或者自定义SQL
   

### 分页查询
    
    分页查询在实际使用中非常普遍了，spring data jpa已经帮我们实现了分页的功能，在查询的方法中，需要传入参数Pageable
    ,当查询中有多个参数的时候Pageable建议做为最后一个参数传入

```java
Page<User> findALL(Pageable pageable);
    
Page<User> findByUserName(String userName,Pageable pageable);
```
`Pageable` 是spring封装的分页实现类，使用的时候需要传入页数、每页条数和排序规则

```java
 //测试分页查询
    @Test
    public void testPage(){
        int page=1,size=3;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> userPage = userRespository.findAll(pageable);
        System.out.println("====TotalElements======"+userPage.getTotalElements());//数据总条数
        System.out.println("====TotalPages===="+userPage.getTotalPages());//总页数
        System.out.println("====getContent===="+userPage.getContent());//得到User集合


    }
```
**限制查询**

有时候我们只需要查询前N个元素，或者支取前一个实体。
```java
ser findFirstByOrderByLastnameAsc();

User findTopByOrderByAgeDesc();

Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);

List<User> findFirst10ByLastname(String lastname, Sort sort);

List<User> findTop10ByLastname(String lastname, Pageable pageable);

```
### 自定义SQL查询
    
  其实Spring data 觉大部分的SQL都可以根据方法名定义的方式来实现，但是由于某些原因我们想使用自定义的SQL来查询，spring data也是完美支持的；在SQL的查询方法上面使用@Query注解，如涉及到删除和修改在需要加上@Modifying.也可以根据需要添加 @Transactional 对事物的支持，查询超时的设置等
  
  **一定要加上事物控制:@Transactional**
```java

    //自定义sql语句查询

    @Transactional
    @Modifying
    @Query("update User u set u.userName = ?1 where u.id = ?2")
    int modifyUserNameAndUserId(String userName, Long id);

    @Transactional
    @Modifying
    @Query("delete from User where id =?1")
    void deleteUserById(Long id);
```



  



    







    