package com.github.renuevo.elastic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.renuevo.dto.ElasticDataDto;
import com.github.renuevo.dto.ElasticParamDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${search.es-index}")
    String index;

    /**
     * <pre>
     *  @methodName : viewDoc
     *  @author : Deokhwa.Kim
     *  @since : 2020-04-29 오전 1:10
     *  @summary : Elastic Doc Id 검색
     *  @param : [id]
     *  @return : reactor.core.publisher.Mono<com.github.renuevo.dto.ElasticDataDto>
     * </pre>
     */
    public Mono<ElasticDataDto> viewDoc(String id) {
        GetRequest getRequest = new GetRequest(index, id);
        return Mono.create(sink -> restHighLevelClient.getAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            @SneakyThrows
            public void onResponse(GetResponse documentFields) {
                log.info("Get Success : {}", documentFields.toString());
                sink.success(objectMapper.readValue(documentFields.getSourceAsString(), ElasticDataDto.class));
            }

            @Override
            public void onFailure(Exception e) {
                log.error("Get Error {}", e.getMessage(), e);
                sink.error(e);
            }
        }));
    }

    /**
     * <pre>
     *  @methodName : searchDoc
     *  @author : Deokhwa.Kim
     *  @since : 2020-04-29 오전 9:53
     *  @summary :
     *  @param : [elasticParamDto]
     *  @return : reactor.core.publisher.Flux<com.github.renuevo.dto.ElasticDataDto>
     * </pre>
     */
    public Flux<ElasticDataDto> searchDoc(ElasticParamDto elasticParamDto) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("word", elasticParamDto.getKeyword()))
                .size(elasticParamDto.getLimit())
                .from(elasticParamDto.getPage());
        searchRequest.source(searchSourceBuilder);

        return Flux.create(sink -> restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<>() {

            @Override
            public void onResponse(SearchResponse searchResponse) {
                log.info("Search Success : {}", searchResponse.toString());
                searchResponse.getHits().forEach(searchHit -> {
                    try {
                        sink.next(objectMapper.readValue(searchHit.getSourceAsString(), ElasticDataDto.class));
                    } catch (Exception e) {
                        log.error("Search Modeling Error {}", e.getMessage(), e);
                    }
                });
                sink.complete();
            }

            @Override
            public void onFailure(Exception e) {
                log.error("Search Error {}", e.getMessage(), e);
                sink.error(e);
            }
        }));
    }


    @SneakyThrows
    public Mono<Void> putDoc(ElasticDataDto elasticDataDto, String id){
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(id);
        indexRequest.source(objectMapper.writeValueAsString(elasticDataDto), XContentType.JSON);
        return Mono.create(sink -> restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                log.info("Index Ok {}", indexResponse.toString());
                sink.success();
            }

            @Override
            public void onFailure(Exception e) {
                log.error("Index Error {}", e.getMessage(), e);
                sink.error(e);
            }
        }));
    }
}
