package com.github.renuevo.config;


import com.github.renuevo.entity.Pay;
import com.github.renuevo.entity.Tax;
import com.github.renuevo.writer.JpaItemListWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
public class JpaListItemWriterJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 2;

    @Bean
    public Job jpaItemListWriterJob() {
        return jobBuilderFactory.get("jpaItemListWriterJob")
                .start(jpaItemListWriterStep())
                .build();
    }

    @Bean
    public Step jpaItemListWriterStep() {
        return stepBuilderFactory.get("jpaListItemWriterStep")
                .<Pay, List<Tax>>chunk(chunkSize)
                .reader(jpaItemListWriterReader())
                .processor(jpaItemListProcessor())
                .writer(jpaItemListWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Pay> jpaItemListWriterReader() {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("jpaItemListWriterReader")
                .entityManagerFactory(entityManagerFactory) //DataSource가 아닌 EntityManagerFactory를 통한 접근
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay p ORDER BY id ASC")    //ORDER 조건은 필수!
                .build();
    }


    @Bean
    public ItemProcessor<Pay, List<Tax>> jpaItemListProcessor(){
        return pay -> List.of(
                new Tax((long)Math.floor(pay.getAmount()*0.4), pay.getId(), "afreecaTv"),
                new Tax((long)Math.floor(pay.getAmount()*0.01), pay.getId(), "twitch")
        );
    }

    public JpaItemListWriter<Tax> jpaItemListWriter() {
        JpaItemWriter<Tax> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return new JpaItemListWriter<>(writer); //ItemListWriter Class 생성해서 처리
    }

}

