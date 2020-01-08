package com.github.renuevo.config;

import com.github.renuevo.reader.ElasticItemReader;
import com.github.renuevo.reader.ElasticItemScrollReader;
import com.github.renuevo.vo.ElasticReaderTestVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class ElasticItemScrollReaderJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RestHighLevelClient restHighLevelClient;

    private final static int chunkSize = 3;

    @Bean
    public Job elasticItemScrollReaderJob() {
        return jobBuilderFactory.get("elasticItemScrollReaderJob")
                .start(elasticItemScrollReaderStep())
                .build();
    }

    @Bean
    public Step elasticItemScrollReaderStep() {
        return stepBuilderFactory.get("elasticItemScrollReaderStep")
                .<ElasticReaderTestVo, ElasticReaderTestVo>chunk(chunkSize)
                .reader(elasticItemScrollReader())
                .writer(elasticItemScrollReaderWriter())  //processor 까지 한번에 처리되고 list로 넘어옴
                .build();
    }

    @Bean
    @SneakyThrows
    public ElasticItemScrollReader<ElasticReaderTestVo> elasticItemScrollReader() {
        return ElasticItemScrollReader.<ElasticReaderTestVo>builder()
                .restHighLevelClient(restHighLevelClient)
                .searchRequest(new SearchRequest("reader_test"))
                .classType(ElasticReaderTestVo.class)
                .pageSize(chunkSize)
                .name("elasticItemScrollReader")
                .build();
    }

    public ItemWriter<ElasticReaderTestVo> elasticItemScrollReaderWriter() {
        return list -> {
            for (ElasticReaderTestVo elasticReaderTestVo : list) {
                log.info("elastic = {}", elasticReaderTestVo);
            }
        };
    }


}
