package br.com.kirio.scheduledquartz.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import br.com.kirio.scheduledquartz.jobs.CustomQuartzJob;

@Configuration
public class QuartzConfig {
					
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobLocator jobLocator;
			
	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
		return jobRegistryBeanPostProcessor;
	}
	
	@Bean
	public JobDetail jobDetailProcessaAgendamento() {	
		return JobBuilder.newJob(CustomQuartzJob.class)
				         .withIdentity("demoJobProcessaAgendamento")
				         .setJobData(getJobProcessaAgendamentoDataMap())
				         .storeDurably()				         
				         .build();		
	}
	
	@Bean
	public JobDetail jobDetailProcessaTransacao() {	
		return JobBuilder.newJob(CustomQuartzJob.class)
				         .withIdentity("demoJobProcessaTransacao")
				         .setJobData(getJobProcessaTransacaoDataMap())
				         .storeDurably()
				         .build();		
	}
	
	@Bean
	public Trigger jobProcessaAgendamentoTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
													                 .withIntervalInSeconds(10)
													                 .repeatForever(); 
        return TriggerBuilder.newTrigger()
                			 .forJob(jobDetailProcessaAgendamento())
                			 .withIdentity("jobProcessaAgendamentoTrigger")
                			 .withSchedule(scheduleBuilder)                			 
                			 .build();
	}
	
	@Bean
    public Trigger jobProcessaTransacaoTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                													 .withIntervalInSeconds(50)
                													 .repeatForever(); 
        return TriggerBuilder.newTrigger()
                			 .forJob(jobDetailProcessaTransacao())
                			 .withIdentity("jobProcessaTransacaoTrigger")
                			 .withSchedule(scheduleBuilder)
                			 .build();
    }		
	
	@Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
		
	@Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {				
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(jobProcessaAgendamentoTrigger(), jobProcessaTransacaoTrigger());  
        scheduler.setQuartzProperties(quartzProperties());
        scheduler.setJobDetails(jobDetailProcessaAgendamento(), jobDetailProcessaTransacao());          
     //   scheduler.setJobFactory(new SchedulerJobFactory());
        return scheduler;
    }
	
	private JobDataMap getJobProcessaAgendamentoDataMap() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "demoJobProcessaAgendamento");
		jobDataMap.put("jobLauncher", jobLauncher);
		jobDataMap.put("jobLocator", jobLocator);
		return jobDataMap;
	}
	
	private JobDataMap getJobProcessaTransacaoDataMap() {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "demoJobProcessaTransacao");
		jobDataMap.put("jobLauncher", jobLauncher);
		jobDataMap.put("jobLocator", jobLocator);
		return jobDataMap;
	}
	
}