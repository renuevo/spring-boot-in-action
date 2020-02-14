package com.github.renuevo.config;


import com.github.renuevo.entity.Pay;
import com.github.renuevo.entity.Pay2;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;


/**
 * <pre>
 * @className : CustomItemWriterJobConfig
 * @author : Deokhwa.Kim
 * @since : 2019-12-31
 * @summary : Custom Item Writer And ItemProcessor Data Transform Example
 * </pre>
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class CustomItemWriterJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job customItemWriterJob() {
        return jobBuilderFactory.get("customItemWriterJob")
                .start(customItemWriterStep())
                .build();
    }

    @Bean
    public Step customItemWriterStep() {
        return stepBuilderFactory.get("customItemWriterStep")
                .<Pay, Pay2>chunk(chunkSize)
                .reader(customItemWriterReader())
                .processor(customItemWriterProcessor())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Pay> customItemWriterReader() {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("customItemWriterReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay p")
                .build();
    }

    @Bean
    public ItemProcessor<Pay, Pay2> customItemWriterProcessor() {
        return pay -> new Pay2(pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
    }

    @Bean
    public ItemWriter<Pay2> customItemWriter() {
        return list -> {                //ItemWriter에서 write는 구현해 주면 된다
            for (Pay2 item : list) {
                log.info("Current Pay = {}", item);
            }
        };
    }

}
