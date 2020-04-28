package com.github.renuevo.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renuevo.dto.ElasticDataDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <pre>
 * @className : ElasticWebFluxService
 * @author : Deokhwa.Kim
 * @since : 2020-04-29
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticWebFluxService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final RestHighLevelClient restHighLevelClient;

    @Value("${search.es-index}")
    String index;

    public Mono<ElasticDataDto> viewDoc(String id) {
        GetRequest getRequest = new GetRequest(index, id);
        return Mono.create(sink -> {
            restHighLevelClient.getAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<>() {
                @Override
                @SneakyThrows
                public void onResponse(GetResponse documentFields) {
                    log.info("get success : {}", documentFields.toString());
                    sink.success(objectMapper.readValue(documentFields.getSourceAsString(),ElasticDataDto.class));
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("get error {}", e.getMessage(), e);
                    sink.error(e);
                }
            });
        });
    }
}
