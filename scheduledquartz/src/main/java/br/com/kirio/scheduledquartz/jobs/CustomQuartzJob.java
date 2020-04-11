package br.com.kirio.scheduledquartz.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CustomQuartzJob extends QuartzJobBean {
	
	private String jobName;
	private JobLauncher jobLauncher;
	private JobLocator jobLocator;

	public String getJobName() {
		return jobName;
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public JobLocator getJobLocator() {
		return jobLocator;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			Job job = jobLocator.getJob(jobName);
			JobParameters parameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis()))
																 .toJobParameters();
			jobLauncher.run(job, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}