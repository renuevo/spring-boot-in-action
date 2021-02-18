package com.github.renuevo.resttemplate;

import com.github.renuevo.common.NaverProperty;
import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean("componentsClientRestTemplate")
    RestTemplate componentsClientRestTemplate(NaverProperty naverProperty) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000);  //1초
        factory.setReadTimeout(1000);     //1초

        Header id = new BasicHeader("X-Naver-Client-Id", naverProperty.getId());
        Header secret = new BasicHeader("X-Naver-Client-Secret", naverProperty.getSecret());

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(5)
                .setDefaultHeaders(Lists.newArrayList(id, secret))
                .build();
        factory.setHttpClient(httpClient);
        return new RestTemplate(factory);
    }

    @Bean("simpleClientRestTemplate")
    RestTemplate simpleClientRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);    //1초
        factory.setReadTimeout(1000);   //1초
        return new RestTemplate(factory);
    }

}
