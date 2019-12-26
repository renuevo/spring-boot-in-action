package com.github.renuevo.config;

import com.github.renuevo.entity.Pay;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
public class JdbcCursorItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;    //트랜잭션 범위

    @Bean
    public Job jdbcCursorItemReaderJob() {
        return jobBuilderFactory.get("jdbcCursorItemReaderJob") // job name
                .start(jdbcCursorItemReaderStep())
                .build();
    }

    @Bean
    public Step jdbcCursorItemReaderStep() {
        return stepBuilderFactory.get("jdbcCursorItemReaderStep")   //step name
                .<Pay, Pay>chunk(chunkSize) //Reader의 반환타입 & Writer의 파라미터타입
                .reader(jdbcCursorItemReader())
                //.processor()  생략
                .writer(jdbcCursorItemWriter())
                .build();
    }


    @Bean
    public JdbcCursorItemReader<Pay> jdbcCursorItemReader() {
        return new JdbcCursorItemReaderBuilder<Pay>()
                .fetchSize(chunkSize)       //Database에서 가져오는 개수 / read()를 통해 1개씩 (Paging과 다름)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .sql("SELECT id, amount, tx_name, tx_date_time FROM pay")
                .name("jdbcCursorItemReader")   //reader name
                .build();
    }


    @Bean
    public ItemWriter<Pay> jdbcCursorItemWriter() {
        return list -> {
            for (Pay pay : list) {
                log.info("Current Pay = {}", pay);
            }
        };
    }

}
