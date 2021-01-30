package com.github.renuevo.feign.client;


import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import com.github.renuevo.feign.annotation.ReactiveSpringQueryMap;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "Sample-Reactive-Feign-Client", url = "https://openapi.naver.com/v1/search")
public interface SampleReactiveFeignClient {

    @GetMapping(value = "blog.json")
    Mono<NaverResponse> naverBlogSearch(
            @RequestHeader("X-Naver-Client-Id") String id,
            @RequestHeader("X-Naver-Client-Secret") String secret,
            @ReactiveSpringQueryMap("param_json") NaverBlogParamDto naverBlogParamDto,
            @SpringQueryMap NaverBlogParamDto naverBlogParamDto2);

}
