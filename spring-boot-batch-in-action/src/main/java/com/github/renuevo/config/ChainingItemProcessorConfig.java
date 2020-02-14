package com.github.renuevo.config;

import com.github.renuevo.entity.Pay;
import com.github.renuevo.entity.Tax;
import com.github.renuevo.process.JpaChainingProcessFirst;
import com.github.renuevo.process.JpaChainingProcessSecond;
import com.google.common.collect.Lists;
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
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;


/**
 * <pre>
 * @className : ChainingItemProcessorConfig
 * @author : Deokhwa.Kim
 * @since : 2020-01-08
 * @summary : Multi ItemProcessor Step Example
 * </pre>
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class ChainingItemProcessorConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 2;

    @Bean
    public Job jpaChainingItemProcessorJob() {
        return jobBuilderFactory.get("jpaChainingItemProcessorJob")
                .start(jpaChainingItemProcessorStep())
                .build();
    }

    @Bean
    public Step jpaChainingItemProcessorStep() {
        return stepBuilderFactory.get("jpaChainingItemProcessorStep")
                .<Pay, Tax>chunk(chunkSize)
                .reader(jpaChainingItemProcessorReader())
                .processor(compositeItemProcessor())
                .writer(jpaChainingItemProcessorWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Pay> jpaChainingItemProcessorReader() {
        return new JpaPagingItemReaderBuilder<Pay>()
                .name("jpaItemWriterReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Pay p ORDER BY id ASC") //Paging Reader Add Order
                .build();
    }

    @Bean
    public CompositeItemProcessor<Pay, Tax> compositeItemProcessor() {
        List<? extends ItemProcessor<?, ?>> itemProcessorList = Lists.newArrayList(new JpaChainingProcessFirst(), new JpaChainingProcessSecond());
        CompositeItemProcessor<Pay, Tax> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(itemProcessorList);
        return compositeItemProcessor;
    }


    @Bean
    public ItemWriter<Tax> jpaChainingItemProcessorWriter() {
        return list -> {
            for (Tax tax : list) {
                log.info("JpaChainingItemProcessorJob {}", tax);
            }
        };
    }

}
