package com.github.renuevo.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "sample-error-reactive-feign-client", url = "http://localhost:8080")
public interface SampleErrorReactiveFeignClient {

    @GetMapping("get500")
    Mono<String> get500();

    @GetMapping("get400")
    Mono<String> get400();
}
