package com.github.renuevo.feign.client;

import com.github.renuevo.common.TestResponseKotlin;
import com.github.renuevo.dto.PostRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "sample-local-feign-client", url = "http://localhost:8080")
public interface SampleLocalFeignClient {

    @PostMapping("/post")
    String postCall(PostRequest postRequest);

    @PostMapping("/post")
    String postMapCall(Map<String, Object> postRequestMap);

    @GetMapping("/kotlin-response")
    TestResponseKotlin testKotlinResponse();

}
