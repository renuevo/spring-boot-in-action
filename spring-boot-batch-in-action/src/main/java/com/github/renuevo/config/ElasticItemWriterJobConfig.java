package com.github.renuevo.config;

import com.github.renuevo.reader.ElasticItemReader;
import com.github.renuevo.vo.ElasticReaderTestVo;
import com.github.renuevo.vo.ElasticWriterTestVo;
import com.github.renuevo.writer.ElasticItemWriter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@AllArgsConstructor
public class ElasticItemWriterJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final RestHighLevelClient restHighLevelClient;

    private static final int chunkSize = 2;

    @Bean
    public Job elasticItemWriterJob() {
        return jobBuilderFactory.get("elasticItemWriterJob")
                .start(elasticItemWriterStep())
                .build();
    }

    @Bean
    public Step elasticItemWriterStep() {
        return stepBuilderFactory.get("elasticItemWriterStep")
                .<ElasticReaderTestVo, ElasticWriterTestVo>chunk(chunkSize)
                .reader(elasticItemWriterReader())
                .processor(elasticItemWriterProcess())
                .writer(elasticItemWriter())  //processor 까지 한번에 처리되고 list로 넘어옴
                .build();
    }

    @Bean
    public ElasticItemReader<ElasticReaderTestVo> elasticItemWriterReader() {
        return ElasticItemReader.<ElasticReaderTestVo>builder()
                .restHighLevelClient(restHighLevelClient)
                .searchRequest(new SearchRequest("reader_test"))
                .classType(ElasticReaderTestVo.class)
                .pageSize(chunkSize)
                .name("elasticItemWriterReader")
                .build();
    }

    @Bean
    public ItemProcessor<ElasticReaderTestVo, ElasticWriterTestVo> elasticItemWriterProcess() {
        return elasticReaderTestVo -> new ElasticWriterTestVo(elasticReaderTestVo.getKey(), elasticReaderTestVo.getName());
    }

    public ItemWriter<ElasticWriterTestVo> elasticItemWriter() {
        return ElasticItemWriter.builder()
                .indexRequest(new IndexRequest("writer_test", "_doc"))
                .restHighLevelClient(restHighLevelClient)
                .build();
    }


}
