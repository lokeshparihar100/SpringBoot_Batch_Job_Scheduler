package com.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.annotation.PostConstruct;

@SpringBootApplication
public class BatchJobScheduleApplication {

	private final JobLauncher jobLauncher;
    private final Job mainJob;

    public BatchJobScheduleApplication(JobLauncher jobLauncher, Job mainJob) {
        this.jobLauncher = jobLauncher;
        this.mainJob = mainJob;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(BatchJobScheduleApplication.class, args);
	}
	
//	@Bean
//    public JobExecutionListener jobExecutionListener() {
//        return new JobCompletionListener();
//    }

    @PostConstruct
    public void executeBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        JobExecution execution = jobLauncher.run(mainJob, jobParameters);
        System.out.println("Job Status: " + execution.getStatus());
    }

}
