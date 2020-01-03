package com.github.renuevo.reader;

import com.github.renuevo.es.EsMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Builder
public class ElasticItemReader<T> extends AbstractPagingItemReader<T> {

    private RestHighLevelClient restHighLevelClient;
    private final EsMapper esMapper = new EsMapper();
    private SearchRequest searchRequest;
    private final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    private String sort;
    private int pageSize;
    private QueryBuilder queryBuilder;
    private Class<T> classType;


    @Override
    protected void doReadPage() {
        try {

            if (results == null) results = new CopyOnWriteArrayList<>();
            else results.clear();

            if (getPage() == 0) {
                if (searchRequest == null) throw new Exception("SearchRequest Not Exist");
                if (classType == null) throw new Exception("ClassType Not Exist");
                if (queryBuilder != null) searchSourceBuilder.query(queryBuilder);
                searchSourceBuilder.sort(Objects.requireNonNullElse(sort, "_id"));
                setPageSize(pageSize);  //Super Page Size Change
            }

            searchSourceBuilder.from(getPage() * getPageSize());
            searchSourceBuilder.size(getPageSize());
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<T> list = esMapper.getSearchSource(searchResponse.toString(), classType);
            results.addAll(list);

        } catch (Exception e) {
            log.info("Elastic doReadPage Error {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }

}
