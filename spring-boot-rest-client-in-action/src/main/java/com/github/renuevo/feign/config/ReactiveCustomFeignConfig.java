package com.github.renuevo.feign.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@Slf4j
public class ReactiveCustomFeignConfig {

    @Primary
    @Bean
    public feign.codec.ErrorDecoder customErrorDecoder() {
        return (methodKey, response) -> {
            log.info("custom feign");
            return new Exception(response.body().toString());
        };
    }

}
