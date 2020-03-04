package com.github.renuevo.config;

import com.github.renuevo.vo.JsonItemVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@AllArgsConstructor
public class JsonFileItemReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private static final int chunkSize = 2;

    @Bean
    public Job jsonFileItemReaderJob() {
        return jobBuilderFactory.get("jsonFileItemReaderJob")
                .start(jsonFileItemReaderStep())
                .build();
    }

    @Bean
    public Step jsonFileItemReaderStep() {
        return stepBuilderFactory.get("jsonFileItemReaderStep")
                .<JsonItemVo, JsonItemVo>chunk(chunkSize)
                .reader(jsonItemReader())
                .writer(jsonItemVos -> jsonItemVos
                        .stream()
                        .map(JsonItemVo::toString)
                        .forEach(log::info))
                .build();
    }


    @Bean
    public JsonItemReader<JsonItemVo> jsonItemReader(){
        return new JsonItemReaderBuilder<JsonItemVo>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(JsonItemVo.class))
                .resource(new ClassPathResource("/read_sample/sample_json_data.json"))
                .name("jsonItemReader")
                .build();
    }

}
