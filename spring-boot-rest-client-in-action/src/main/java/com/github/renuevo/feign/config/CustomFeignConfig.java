package com.github.renuevo.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
public class CustomFeignConfig {

    private final ObjectMapper objectMapper;

    @Primary
    @Bean
    public ErrorDecoder customErrorDecoder(){
        return new CustomFeignErrorDecoder(objectMapper);
    }

}
