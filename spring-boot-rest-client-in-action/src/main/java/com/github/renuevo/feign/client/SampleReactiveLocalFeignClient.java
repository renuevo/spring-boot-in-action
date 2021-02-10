package com.github.renuevo.feign.client;


import com.github.renuevo.common.TestResponse;
import com.github.renuevo.dto.PostRequest;
import com.github.renuevo.feign.config.ReactiveFeignConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@ReactiveFeignClient(name = "sample-local-reactive-feign-client", url = "http://localhost:8080", configuration = ReactiveFeignConfig.class)
public interface SampleReactiveLocalFeignClient {
    @PostMapping("/post")
    Mono<String> postCall(PostRequest postRequest);

    @PostMapping("/post")
    Mono<String> postMapCall(Map<String, Object> postRequestMap);

    @GetMapping("/camel/test")
    Mono<TestResponse> camelCall();

    @GetMapping("/snake/test")
    Mono<TestResponse> snakeCall();


}
