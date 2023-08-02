package com.batch.config;


import com.batch.job.SpringBatchJobScheduler;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzJobConfig {

    @Autowired
    private SpringBatchJobScheduler springBatchJobScheduler;

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SpringBatchJobScheduler.class);
        jobDetailFactory.setDescription("Invoke Spring Batch Job Service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean trigger1(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setName("Trigger-1");
        trigger.setCronExpression("0 07 09 ? * WED,FRI"); // Cron expression for 13:15 and 22:40 every month on Monday and Friday
        return trigger;
    }
    
    @Bean
    public CronTriggerFactoryBean trigger2(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setName("Trigger-2");
        trigger.setCronExpression("0 15 09 ? * WED,FRI"); // Cron expression for 13:15 and 22:40 every month on Monday and Friday
        return trigger;
    }
    
    @Bean
    public CronTriggerFactoryBean trigger3(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setName("Trigger-3");
        trigger.setCronExpression("0 16 09 ? * WED,FRI"); // Cron expression for 13:15 and 22:40 every month on Monday and Friday
        return trigger;
    }
    
    @Bean
    public CronTriggerFactoryBean trigger4(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setName("Trigger-4");
        trigger.setCronExpression("0 17 09 ? * WED,FRI"); // Cron expression for 13:15 and 22:40 every month on Monday and Friday
        return trigger;
    }
    
    @Bean
    public CronTriggerFactoryBean trigger5(JobDetail jobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setName("Trigger-5");
        trigger.setCronExpression("0 18 09 ? * WED,FRI"); // Cron expression for 13:15 and 22:40 every month on Monday and Friday
        return trigger;
    }
}
