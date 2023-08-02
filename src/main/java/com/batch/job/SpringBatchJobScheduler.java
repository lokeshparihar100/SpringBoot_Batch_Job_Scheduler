package com.batch.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SpringBatchJobScheduler implements Job {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private org.springframework.batch.core.Job mainJob; // Inject the main job to be executed

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // Add current timestamp as job parameter
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("currentTime", new Date())
                    .toJobParameters();

            // Launch the Spring Batch job
//            jobLauncher.run(personNameJob, jobParameters);
            JobExecution execution = jobLauncher.run(mainJob, jobParameters);
            System.out.println("Job Status: " + execution.getStatus());
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
        
    }
}
