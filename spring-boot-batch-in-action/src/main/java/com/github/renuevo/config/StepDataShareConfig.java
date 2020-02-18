package com.github.renuevo.config;


import com.github.renuevo.data.StepShareData;
import com.github.renuevo.entity.Pay;
import com.github.renuevo.entity.Pay2;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * <pre>
 * @className : StepDataShareConfig
 * @author : Deokhwa.Kim
 * @since : 2020-01-18
 * @summary : Step Data Share Example
 * </pre>
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class StepDataShareConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final StepShareData stepShareData;

    private static final int chunkSize = 4;

    @Bean
    public Job stepDataShareJob() {
        return jobBuilderFactory.get("stepDataShareJob")
                .start(stepDataShareFirstStep())
                    .on("COMPLETED")
                    .to(stepDataShareSecondStep())
                    .on("*")
                    .end()
                .end()
                .build();
    }

    @Bean
    public Step stepDataShareFirstStep() {
        return stepBuilderFactory.get("stepDataShareFirstStep")
                .<Pay, Pay>chunk(chunkSize)
                .reader(shareItemReader())
                .writer(shareItemFirstWriter())
                .build();
    }

    @Bean
    public Step stepDataShareSecondStep() {
        return stepBuilderFactory.get("stepDataShareSecondStep")
                .<Pay, Pay>chunk(chunkSize)
                .reader(shareItemReader())
                .writer(shareItemSecondWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Pay> shareItemReader() {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("jpaItemWriterReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay p ORDER BY id ASC") //Paging Reader Add Order
                .build();
    }

    @Bean
    public ItemWriter<Pay> shareItemFirstWriter() {
        return list -> {
            for (Pay pay : list) {
                Pay2 pay2 = new Pay2(pay.getId(), pay.getAmount(), "Save " + pay.getTxName(), pay.getTxDateTime());
                stepShareData.putData(pay2.getId(), pay2);
                log.info("Save Share Data {}", pay2);
            }
        };
    }

    @Bean
    public ItemWriter<Pay> shareItemSecondWriter() {
        return list -> {
            for (Pay pay : list) {
                log.info("First Read {}", stepShareData.get(pay.getId()));
                log.info("Second Read {}", pay);
            }
        };
    }

}
