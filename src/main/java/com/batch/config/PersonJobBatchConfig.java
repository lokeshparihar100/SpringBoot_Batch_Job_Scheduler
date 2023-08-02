package com.batch.config;


 


import com.batch.model.PersonJob;
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
public class PersonJobBatchConfig {

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
    public ItemReader<PersonJob> personJobItemReader() {
        return new MySQLItemReader<>(mysqlDataSource, "select job_title, company, team from person_job", PersonJob.class);
    }

    @Bean
    public PersonItemProcessor<PersonJob> personJobItemProcessor() {
        return new PersonItemProcessor<PersonJob>();
    }

    @Bean
    public ItemWriter<PersonJob> personJobItemWriter() {
        return new PostgreSQLItemWriter<>(postgresqlDataSource);
    }

    @Bean
    public Step personJobStep(ItemReader<PersonJob> personJobItemReader, PersonItemProcessor<PersonJob> personItemProcessor, ItemWriter<PersonJob> personJobItemWriter) {
        return stepBuilderFactory.get("personJobStep")
                .<PersonJob, PersonJob>chunk(10)
                .reader(personJobItemReader)
                .processor(personItemProcessor)
                .writer(personJobItemWriter)
                .build();
    }

 // Define the personNameJob Step here
    @Bean
    public Step personJobJobStep() {
        return stepBuilderFactory.get("personJobJobStep")
                .job(personJobJob())
                .build();
    }

    // Define the personNameJob here as a Job
    @Bean
    public Job personJobJob() {
        return jobBuilderFactory.get("personJobJob")
                .start(personJobStep(personJobItemReader(), personJobItemProcessor(), personJobItemWriter()))
                .build();
    }
}

