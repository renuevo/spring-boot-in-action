package com.github.renuevo.feign.client.error;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "error-retry-client", url = "http://localhost:8080")
public interface SampleErrorRetryFeignClient {

    @GetMapping("get500")
    String get500();

    @GetMapping("get400")
    String get400();

}
