package com.batch.config;


import com.batch.model.PersonName;
import com.batch.processor.PersonItemProcessor;
import com.batch.reader.MySQLItemReader;
import com.batch.writer.PostgreSQLItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class PersonNameBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private DataSource dataSource;
    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource mysqlDataSource;

    @Autowired
    @Qualifier("postgresqlDataSource")
    private DataSource postgresqlDataSource;

    @Bean
    public ItemReader<PersonName> personNameItemReader() {
        return new MySQLItemReader<>(mysqlDataSource, "select first_Name, last_Name from person_name", PersonName.class);
    }

    @Bean
    public PersonItemProcessor<PersonName> personNameItemProcessor() {
        return new PersonItemProcessor<PersonName>();
    }

    @Bean
    public ItemWriter<PersonName> personNameItemWriter() {
        return new PostgreSQLItemWriter<>(postgresqlDataSource);
    }

    @Bean
    public Step personNameStep(ItemReader<PersonName> personNameItemReader, PersonItemProcessor<PersonName> personItemProcessor, ItemWriter<PersonName> personNameItemWriter) {
        return stepBuilderFactory.get("personNameStep")
                .<PersonName, PersonName>chunk(10)
                .reader(personNameItemReader)
                .processor(personItemProcessor)
                .writer(personNameItemWriter)
                .build();
    }

    // Define the personNameJob Step here
    @Bean
    public Step personNameJobStep() {
        return stepBuilderFactory.get("personNameJobStep")
                .job(personNameJob())
                .build();
    }

    // Define the personNameJob here as a Job
    @Bean
    public Job personNameJob() {
        return jobBuilderFactory.get("personNameJob")
                .start(personNameStep(personNameItemReader(), personNameItemProcessor(), personNameItemWriter()))
                .build();
    }
}

