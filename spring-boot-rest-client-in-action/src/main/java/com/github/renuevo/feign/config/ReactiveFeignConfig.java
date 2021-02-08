package com.github.renuevo.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.ReactiveOptions;
import reactivefeign.retry.BasicReactiveRetryPolicy;
import reactivefeign.retry.ReactiveRetryPolicy;
import reactivefeign.webclient.WebReactiveOptions;

@Configuration
public class ReactiveFeignConfig {

    @Bean
    public ReactiveOptions reactiveOptions() {
        return new WebReactiveOptions.Builder()
                .setReadTimeoutMillis(3000)
                .setConnectTimeoutMillis(10000)
                .build();
    }

    /**
     * {@link https://github.com/reactor/reactor}
     * this is required reactor-bom library `reactor.util.retry` -> reactor-core 2.3.4
     */
    @Bean
    public ReactiveRetryPolicy reactiveRetryPolicy() {
        return BasicReactiveRetryPolicy.retryWithBackoff(3, 5);
    }

    @Bean
    public feign.codec.ErrorDecoder reactiveStatusHandler() {
        return new FeignErrorDecoder();
    }
}
