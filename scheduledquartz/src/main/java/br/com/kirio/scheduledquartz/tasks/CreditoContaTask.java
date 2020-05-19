package br.com.kirio.scheduledquartz.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class CreditoContaTask implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		System.out.println("Iniciando Crédito em Conta...");
		
		
		
		System.out.println("Finalizado com sucesso - Crédito em Conta");		
		return RepeatStatus.FINISHED;
	}

}
