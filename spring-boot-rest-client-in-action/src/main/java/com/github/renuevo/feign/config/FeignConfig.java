package com.github.renuevo.feign.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.github.renuevo.feign.annotation.CustomParamAnnotationProcess;
import com.google.common.collect.Lists;
import feign.Contract;
import feign.QueryMapEncoder;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Configuration
public class FeignConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignConfig() {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .registerModule(new KotlinModule());
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(mapper);
        messageConverters = () -> new HttpMessageConverters(jacksonConverter);
    }

    @Bean
    Encoder encoder() {
        return new SpringEncoder(messageConverters);
    }

    @Bean
    Decoder decoder() {
        return new SpringDecoder(messageConverters);
    }


    /**
     * 내부 QueryMap의 대한 Encoder 지정
     * default는 {@link feign.querymap.FieldQueryMapEncoder}
     * ReactiveFeignClient에서는 Encoder Override가 불가능함
     */
    @Bean
    QueryMapEncoder queryMapEncoder(ObjectMapper objectMapper) {
        return object -> objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {
        });
    }

    @Bean
    Contract springMvcContractConfig() {
        List<AnnotatedParameterProcessor> annotatedParameterProcessorList = Lists.newArrayList(new CustomParamAnnotationProcess());
        return new SpringMvcContract(annotatedParameterProcessorList);  //add custom annotation
    }

    @Bean
    Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

}
