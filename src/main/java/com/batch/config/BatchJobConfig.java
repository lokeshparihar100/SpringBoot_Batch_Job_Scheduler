package com.batch.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonNameBatchConfig personNameBatchConfig;

    @Autowired
    private PersonDetailsBatchConfig personDetailsBatchConfig;

    @Autowired
    private PersonJobBatchConfig personJobBatchConfig;

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("Task 1 - Execute Query 1");
                    return null;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("Task 2 - Execute Query 2");
                    return null;
                })
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("Task 3 - Execute Query 3");
                    return null;
                })
                .build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobCompletionListener();
    }

    @Bean
    public Job mainJob() {
        return jobBuilderFactory.get("mainJob")
                .listener(jobExecutionListener())
                .start(step1())
                .next(personNameBatchConfig.personNameJobStep())
                .next(step2())
                .next(personDetailsBatchConfig.personDetailsJobStep())
                .next(step3())
                .next(personJobBatchConfig.personJobJobStep())
                .build();
    }
    
//    @Bean
//    public Step personNameJobStep() {
//        return stepBuilderFactory.get("personNameJobStep")
//                .job(personNameBatchConfig.personNameJob())
//                .build();
//    }
//
//    @Bean
//    public Step personDetailsJobStep() {
//        return stepBuilderFactory.get("personDetailsJobStep")
//                .job(personDetailsBatchConfig.personDetailsJob())
//                .build();
//    }
//
//    @Bean
//    public Step personJobJobStep() {
//        return stepBuilderFactory.get("personJobJobStep")
//                .job(personJobBatchConfig.personJobJob())
//                .build();
//    }
}

