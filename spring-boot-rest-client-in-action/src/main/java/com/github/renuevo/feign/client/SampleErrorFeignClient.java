package com.github.renuevo.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "Error-Client", url = "http://localhost:8080")
public interface SampleErrorFeignClient {

    @GetMapping("get500")
    String get500();

    @GetMapping("get400")
    String get400();
}
