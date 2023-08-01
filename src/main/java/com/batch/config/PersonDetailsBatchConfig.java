package com.batch.config;

import com.batch.model.PersonDetails;
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
public class PersonDetailsBatchConfig {

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
    public ItemReader<PersonDetails> personDetailsItemReader() {
        return new MySQLItemReader<>(mysqlDataSource, "select address, mobile_number from person_details", PersonDetails.class);
    }

    @Bean
    public PersonItemProcessor<PersonDetails> personDetailsItemProcessor() {
        return new PersonItemProcessor<PersonDetails>();
    }

    @Bean
    public ItemWriter<PersonDetails> personDetailsItemWriter() {
        return new PostgreSQLItemWriter<>(postgresqlDataSource);
    }

    @Bean
    public Step personDetailsStep(ItemReader<PersonDetails> personDetailsItemReader, PersonItemProcessor<PersonDetails> personItemProcessor, ItemWriter<PersonDetails> personDetailsItemWriter) {
        return stepBuilderFactory.get("personDetailsStep")
                .<PersonDetails, PersonDetails>chunk(10)
                .reader(personDetailsItemReader)
                .processor(personItemProcessor)
                .writer(personDetailsItemWriter)
                .build();
    }

 // Define the personDetailsJob Step here
    @Bean
    public Step personDetailsJobStep() {
        return stepBuilderFactory.get("personDetailsJobStep")
                .job(personDetailsJob())
                .build();
    }

    // Define the personDetailsJob here as a Job
    @Bean
    public Job personDetailsJob() {
        return jobBuilderFactory.get("personDetailsJob")
                .start(personDetailsStep(personDetailsItemReader(), personDetailsItemProcessor(), personDetailsItemWriter()))
                .build();
    }
}

