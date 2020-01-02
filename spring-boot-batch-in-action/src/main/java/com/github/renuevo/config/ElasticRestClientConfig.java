package com.github.renuevo.config;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@AllArgsConstructor
public class ElasticRestClientConfig {

    private final Environment env;

    @SuppressWarnings("unchecked")
    //@Bean(name = "customRestClientBuilder")
    public RestClientBuilder restClientBuilder() {
        List<HttpHost> httpHostList = Lists.newArrayList();
        List<String> hostList = env.getProperty("search.es-host", ArrayList.class);
        Header[] headers = {new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"), new BasicHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8")};

        Objects.requireNonNull(hostList).forEach(it -> {
            httpHostList.add(new HttpHost(it, Ints.tryParse(Objects.requireNonNull(env.getProperty("search.es-port"))), "http"));
        });

        return RestClient.builder(httpHostList.toArray(new HttpHost[0]))
                .setMaxRetryTimeoutMillis(10000)
                .setDefaultHeaders(headers);
    }
/*
    @Bean
    public RestClient restClient() {
        return restClientBuilder().build();
    }
 */
}