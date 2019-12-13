package com.github.renuevo.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class JobDeciderConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobDeciderBean() {
        return jobBuilderFactory.get("deTestJob")
                .start(startStep())
                .next(decider())    //조건 실행
                .from(decider())    //조건 상태값 확인
                    .on("testDecide")
                    .to(startStep2())
                .from(decider())
                    .on("testDecide2")
                    .to(startStep3())
                .end()
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("[========= This is DeTestJob ==========]");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step startStep2() {
        return stepBuilderFactory.get("startStep2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("[========= This is DeTestJob2 ==========]");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public Step startStep3() {
        return stepBuilderFactory.get("startStep3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("[========= This is DeTestJob3 ==========]");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public JobExecutionDecider decider() {
        return new jobDecider();
    }

    public static class jobDecider implements JobExecutionDecider{

        /**
         * <pre>
         *  @methodName : decide
         *  @author : Deokhwa.Kim
         *  @since : 2019-12-13 오후 5:38
         *  @summary : 조건 setting
         *  @param : [jobExecution, stepExecution]
         *  @return : org.springframework.batch.core.job.flow.FlowExecutionStatus
         * </pre>
         */
        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            return new FlowExecutionStatus("testDecide");
        }
    }


}
