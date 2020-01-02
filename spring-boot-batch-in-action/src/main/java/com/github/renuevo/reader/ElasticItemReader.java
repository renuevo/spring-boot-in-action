package com.github.renuevo.reader;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestClient;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
public class ElasticItemReader<T> extends AbstractPagingItemReader<T> {

    private final String queryTemplate;
    private final RestClient restClient;

    public ElasticItemReader(
            String queryTemplate,
            @Autowired @Qualifier("customRestClientBuilder") RestClient restClient) {
        this.restClient = restClient;
        this.queryTemplate = queryTemplate;

    }

    @Override
    protected void doReadPage() {

    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }

}
