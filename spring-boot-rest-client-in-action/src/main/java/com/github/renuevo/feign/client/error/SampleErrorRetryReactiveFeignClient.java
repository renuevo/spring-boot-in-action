package com.github.renuevo.feign.client.error;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "sample-error-reactive-retry-feign-client", url = "http://localhost:8080")
public interface SampleErrorRetryReactiveFeignClient {

    @GetMapping("get500")
    Mono<String> get500();

    @GetMapping("get400")
    Mono<String> get400();

}
