package com.github.renuevo.feign;

import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleCircuitFeignClient {
    private final SampleFeignClient sampleFeignClient;

    @CircuitBreaker(name = "Sample-Circuit-Feign-Client", fallbackMethod = "fallback")
    public NaverResponse naverBlogSearch(String id, String secret, NaverBlogParamDto naverBlogParamDto) {
        return sampleFeignClient.naverBlogSearch(id, secret, naverBlogParamDto);
    }

    public NaverResponse fallback(Exception exception) {
        return new NaverResponse();
    }
}
