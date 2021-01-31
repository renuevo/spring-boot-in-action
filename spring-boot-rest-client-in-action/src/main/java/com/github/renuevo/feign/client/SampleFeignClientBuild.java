package com.github.renuevo.feign.client;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 * @className : SampleFeignClientConfig
 * @author : Deokhwa.Kim
 * @since : 2020-03-19
 * </pre>
 */
@Configuration
@RequiredArgsConstructor
@Import(FeignClientsConfiguration.class)
public class SampleFeignClientBuild {

    private final Encoder encoder;
    private final Decoder decoder;
    private final Contract contract;

    @Bean
    public SampleBuildFeignClient recruitSearchClient(@Value("${naver.url.search}") String searchUrl) {
        //https://github.com/OpenFeign/feign#dynamic-query-parameters
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(SampleBuildFeignClient.class, searchUrl);
    }
}
