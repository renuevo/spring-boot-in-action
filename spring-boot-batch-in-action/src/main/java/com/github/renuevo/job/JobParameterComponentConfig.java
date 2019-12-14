package com.github.renuevo.job;

import com.github.renuevo.component.ParameterTasklet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@AllArgsConstructor
public class JobParameterComponentConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ParameterTasklet parameterTasklet;

    @Bean
    public Job JobParameterComponentBean() {
        return jobBuilderFactory.get("jobParameterScope")
                .start(scopeStep())
                .build();
    }

    @Bean
    public Step scopeStep() {
        return stepBuilderFactory.get("scopeStep")
                .tasklet(parameterTasklet)
                .build();
    }
}
