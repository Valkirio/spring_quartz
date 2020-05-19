package br.com.kirio.scheduledquartz.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.kirio.scheduledquartz.tasks.AgendamentoMovimentoTask;
import br.com.kirio.scheduledquartz.tasks.CreditoContaTask;
import br.com.kirio.scheduledquartz.tasks.DebitoContaTask;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory steps;
	
	@Bean(name = "stepAgendamentoMovimento")
	public Step stepAgendamentoMovimento() {
		return steps.get("stepAgendamentoMovimento")
					.tasklet(new AgendamentoMovimentoTask())
					.build();
	}
	
	@Bean(name = "stepCreditoConta")
	public Step stepCreditoConta() {
		return steps.get("stepCreditoConta")
				    .tasklet(new CreditoContaTask())
				    .build();
	}
	
	@Bean(name = "stepDebitoConta")
	public Step stepDebitoConta() {
		return steps.get("stepDebitoConta")
				    .tasklet(new DebitoContaTask())
				    .build();
	}
	
	@Bean(name = "demoJobProcessaTransacao")
	public Job demoJobProcessaTransacao() {
		return jobs.get("demoJobProcessaTransacao")
				   .start(stepCreditoConta())
				   .next(stepDebitoConta())
				   .build();
	}
	
	@Bean(name = "demoJobProcessaAgendamento")
	public Job demoJobProcessaAgendamento() {
		return jobs.get("demoJobProcessaAgendamento")
				   .flow(stepAgendamentoMovimento())
				   .build()
				   .build();
	}

}