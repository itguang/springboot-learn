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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源配置
 */

@Configuration
//配置 Mapper.java 扫描
@MapperScan(basePackages = "com.example.springboot_06.mapper.test1",sqlSessionTemplateRef = "test1SqlSessionTemplate")
public class DataSource1Config {


    //1,创建 DataSource

    //2,创建SqlSessionFaction

    //3,创建事物管理

    //4,包装到SqlSessionTemplate

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
