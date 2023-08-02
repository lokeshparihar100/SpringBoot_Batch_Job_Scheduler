package com.batch.config;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PostgreSQLDataSourceConfig {

//	@ConfigurationProperties(prefix = "spring.datasource.postgresql")
    @Bean(name = "postgresqlDataSource")
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/datasource2");
        dataSource.setUsername("lokeshparihar");
        dataSource.setPassword("LPdatabase1234");
//        dataSource.setSchema("classpath:org/springframework/batch/core/schema-postgresql.sql");

        return dataSource;
    }
}

