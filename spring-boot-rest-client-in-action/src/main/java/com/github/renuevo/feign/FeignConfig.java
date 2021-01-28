package com.github.renuevo.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.QueryMapEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class FeignConfig {
    private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

    @Bean
    Encoder encoder() {
        return new SpringEncoder(messageConverters);
    }

    @Bean
    Decoder decoder() {
        return new SpringDecoder(messageConverters);
    }

    @Bean
    public QueryMapEncoder queryMapEncoder(ObjectMapper objectMapper) {
        return object -> objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }

}
