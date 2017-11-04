# Spring Boot测试

Spring的 SpringJunit4ClassRunner 可以在基于Junit的应用程序测试里加载Spring应用程序上下文.

在Spring Boot应用程序中,Spring Boot除了用于Spring的集成测试支持,还开启了自动配置和web服务器,并提供了不少实用的测试辅助工具.



## 集成测试自动配置


Spring Framework工作的核心是 将所有组件编织在一起,构成一个应用程序.整个过程就是读取配置说明
可以是 XML,基于java的配置,基于Groovy的配置或基于其他的配置,然后再应用程序的上下文中初始化bean,将bean注入到依赖他们的其他bean中.


