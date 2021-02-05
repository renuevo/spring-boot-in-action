package com.github.renuevo.feign.client;


import com.github.renuevo.dto.PostRequest;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@ReactiveFeignClient(name = "sample-local-reactive-feign-client", url = "http://localhost:8080")
public interface SampleReactiveLocalFeignClient {
    @PostMapping("post")
    Mono<String> postCall(PostRequest postRequest);

    @PostMapping("post")
    Mono<String> postMapCall(Map<String, Object> postRequestMap);
}
