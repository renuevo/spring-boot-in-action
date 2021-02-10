package com.github.renuevo.feign.client.error;

import com.github.renuevo.feign.config.ReactiveCustomFeignConfig;
import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "sample-error-reactive-custom-feign-client",
        url = "http://localhost:8080",
        configuration = ReactiveCustomFeignConfig.class)
public interface SampleErrorReactiveFeignCustomClient {

    @GetMapping("get500")
    Mono<String> get500();

    @GetMapping("get400")
    Mono<String> get400();
}
