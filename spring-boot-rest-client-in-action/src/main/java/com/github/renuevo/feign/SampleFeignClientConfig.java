package com.github.renuevo.feign;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Import(FeignClientsConfiguration.class)
public class SampleFeignClientConfig {

    private final Encoder encoder;
    private final Decoder decoder;
    private final Contract contract;

    @Bean
    public SampleBuildFeignClient recruitSearchClient() {
        //https://github.com/OpenFeign/feign#dynamic-query-parameters
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(SampleBuildFeignClient.class, "https://openapi.naver.com/v1/search");
    }
}
