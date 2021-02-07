package com.github.renuevo.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.ReactiveOptions;
import reactivefeign.webclient.WebReactiveOptions;

@Configuration
public class ReactiveOptionsConfig {

    @Bean
    public ReactiveOptions.Builder defaultBuilder() {
        return new WebReactiveOptions.Builder()
                .setReadTimeoutMillis(5000)
                .setWriteTimeoutMillis(10000)
                .setConnectTimeoutMillis(10000);
    }

}
