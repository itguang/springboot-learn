package com.example.springboot_06.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

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
