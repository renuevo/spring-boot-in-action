package com.github.renuevo.reader;

import com.github.renuevo.es.EsMapper;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Builder
public class ElasticItemReader<T> extends AbstractPaginatedDataItemReader<T> {

    private RestHighLevelClient restHighLevelClient;
    private final EsMapper esMapper = new EsMapper();
    private SearchRequest searchRequest;
    private final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    private String sort;
    private int pageSize;
    private QueryBuilder queryBuilder;
    private Class<T> classType;
    private String name;

    @Override
    protected Iterator<T> doPageRead() {
        List<T> list = Lists.newArrayList();
        try {

            if (page == 0) {
                if (queryBuilder != null) searchSourceBuilder.query(queryBuilder);
                searchSourceBuilder.sort(Objects.requireNonNullElse(sort, "_doc"));
            }

            searchSourceBuilder.from(page * pageSize);
            searchSourceBuilder.size(pageSize);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            list = esMapper.getSearchSource(searchResponse.toString(), classType);

        } catch (Exception e) {
            log.info("Elastic doReadPage Error {}", e.getMessage(), e);
        }

        return list.iterator();
    }

    @Override
    protected void doOpen() throws Exception {
        if (searchRequest == null) throw new Exception("SearchRequest Not Exist");
        if (classType == null) throw new Exception("ClassType Not Exist");
        setName(this.name);
    }

}
