package com.github.renuevo.webclient.config;

import com.github.renuevo.common.NaverProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient naverWebClient(@Value("${naver.url.search}") String naverUrl, NaverProperty naverProperty) {
        return WebClient.builder().baseUrl(naverUrl).defaultHeaders(httpHeaders -> {
            httpHeaders.add("X-Naver-Client-Id", naverProperty.getId());
            httpHeaders.add("X-Naver-Client-Secret", naverProperty.getSecret());
        }).build();
    }

    @Bean
    WebClient commonWebClient() {
        return WebClient.create();
    }

}
