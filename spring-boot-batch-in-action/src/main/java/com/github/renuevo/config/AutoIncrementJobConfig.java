package com.github.renuevo.config;

import com.github.renuevo.entity.Pay;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@Configuration
@AllArgsConstructor
public class AutoIncrementJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

    @Bean
    public Job autoIncrementJob() {
        return jobBuilderFactory.get("autoIncrementJob")
                .incrementer(new RunIdIncrementer())
                .start(autoIncrementStep())
                .build();
    }

    @Bean
    public Step autoIncrementStep() {
        return stepBuilderFactory.get("autoIncrementStep")
                .<Pay, Pay>chunk(chunkSize)
                .reader(autoIncrementReader())
                .writer(autoIncrementWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Pay> autoIncrementReader() {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
                .name("jdbcBatchItemWriter")
                .build();
    }

    @Bean
    public ItemWriter<Pay> autoIncrementWriter() {
        return list -> {
            for (Pay pay : list) {
                log.info("Current Pay = {}", pay);
            }
        };
    }


}
