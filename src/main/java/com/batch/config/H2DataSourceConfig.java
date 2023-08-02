package com.batch.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class H2DataSourceConfig {

	@Primary
	@Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://mysql_host:port/db_name?useSSL=false");
//        dataSource.setUsername("db_username");
//        dataSource.setPassword("db_password");
        return dataSource;
    }
}
