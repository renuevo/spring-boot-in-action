package com.github.renuevo.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * @className : ElasticRestClientConfig
 * @author : Deokhwa.Kim
 * @since : 2020-01-03
 * @summary : Elastic Rest Client Configuration
 * </pre>
 */
@Configuration
@NoArgsConstructor
public class ElasticRestClientConfig {

    @Value("${search.es-port}")
    private Integer elasticPort;

    @Value("${search.es-host}")
    public List<String> elasticHostList;

    @SuppressWarnings("unchecked")
    //@Bean(name = "customRestClientBuilder")
    public RestClientBuilder restClientBuilder() {
        List<HttpHost> httpHostList = Lists.newArrayList();
        Header[] headers = {new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"), new BasicHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8")};

        Objects.requireNonNull(elasticHostList).forEach(it -> {
            httpHostList.add(new HttpHost(it, elasticPort, "http"));
        });

        return RestClient.builder(httpHostList.toArray(new HttpHost[0]))
                .setMaxRetryTimeoutMillis(10000)
                .setDefaultHeaders(headers);
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(restClientBuilder());
    }

}