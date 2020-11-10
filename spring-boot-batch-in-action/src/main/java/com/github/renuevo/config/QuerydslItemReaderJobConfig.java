package com.github.renuevo.config;

import com.github.renuevo.entity.Pay;
import com.github.renuevo.querydsl.QuerydslNoOffsetPagingItemReader;
import com.github.renuevo.querydsl.expression.Expression;
import com.github.renuevo.querydsl.options.QuerydslNoOffsetNumberOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuerydslItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int CHUNK_SIZE = 10;

    @Bean
    public Job querydslItemReaderJob() {
        return jobBuilderFactory.get("querydslItemReaderJob")
                .start(querydslItemReaderStep())
                .build();
    }

    @Bean
    public Step querydslItemReaderStep() {
        return stepBuilderFactory.get("jpaPagingItemReaderStep")
                .<Pay, Pay>chunk(CHUNK_SIZE)
                .reader(querydslNoOffsetPagingItemReader())
                .writer(querydslItemWriter())
                .build();
    }

    @Bean
    public QuerydslNoOffsetPagingItemReader<Pay> querydslNoOffsetPagingItemReader() {
        return null;
    }

    @Bean
    public ItemWriter<Pay> querydslItemWriter() {
        return list -> {
            for (Pay pay : list) {
                log.info("Current Pay = {}", pay);
            }
        };
    }


}
