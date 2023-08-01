package com.batch.config;


//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;

@Configuration
public class MySQLDataSourceConfig {

//  @ConfigurationProperties(prefix = "spring.datasource.mysql")
    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/datasource1?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("LPdatabase1234");
//        dataSource.setSchema("classpath:org/springframework/batch/core/schema-mysql.sql");
        return dataSource;
    }
}

