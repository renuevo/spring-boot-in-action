package com.github.renuevo.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renuevo.vo.ElasticWriterTestVo;
import lombok.Builder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.Map;

@Builder
public class ElasticItemWriter implements ItemWriter<ElasticWriterTestVo> {

    private RestHighLevelClient restHighLevelClient;
    private IndexRequest indexRequest;
    private ObjectMapper objectMapper;

    @Override
    public void write(List<? extends ElasticWriterTestVo> items) throws Exception {
        objectMapper = new ObjectMapper();
        for (ElasticWriterTestVo elasticWriterTestVo : items) {
            indexRequest.source(objectMapper.convertValue(elasticWriterTestVo, Map.class));
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        }
    }

}
