package br.com.kirio.scheduledquartz.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MyTaskTwo implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context) throws Exception {
		System.out.println("MytaskTwo start...");
		
		// Implementar codigo aqui
		
		
		System.out.println("MytaskTwo Finish!");
		
		return RepeatStatus.FINISHED;
	}

}
