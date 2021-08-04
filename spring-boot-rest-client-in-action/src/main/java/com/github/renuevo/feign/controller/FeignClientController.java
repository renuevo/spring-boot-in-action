package com.github.renuevo.feign.controller;


import com.github.renuevo.common.NaverProperty;
import com.github.renuevo.common.TestResponse;
import com.github.renuevo.common.TestResponseKotlin;
import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import com.github.renuevo.dto.PostRequest;
import com.github.renuevo.feign.client.*;
import feign.QueryMapEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FeignClientController {

    private final SampleFeignClient sampleFeignClient;
    private final SampleBuildFeignClient sampleBuildFeignClient;
    private final SampleCircuitFeignClient sampleCircuitFeignClient;
    private final SampleReactiveFeignClient sampleReactiveFeignClient;

    private final SampleLocalFeignClient sampleLocalFeignClient;
    private final SampleReactiveLocalFeignClient sampleReactiveLocalFeignClient;

    private final QueryMapEncoder queryMapEncoder;
    private final NaverProperty naverProperty;

    @GetMapping("/client/search")
    public NaverResponse callSampleFeignClient(@RequestParam("query") String query) {
        return sampleFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build());
    }

    @GetMapping("/build-client/search")
    public NaverResponse callSampleBuildFeignClient(@RequestParam("query") String query) {
        return sampleBuildFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build());
    }

    @GetMapping("/circuit-client/search")
    public NaverResponse callCircuitFeignClient(@RequestParam("query") String query) {
        return sampleCircuitFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build());
    }

    @GetMapping("/reactive-client/search")
    public Mono<NaverResponse> callReactiveFeignClient(@RequestParam("query") String query) {
        Mono<NaverResponse> naverResponseMono = sampleReactiveFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), queryMapEncoder.encode(NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build()))
                .map(data -> {
                    log.info("2 : in mono");
                    return data;
                });
        log.info("1 : first point");
        return naverResponseMono;
    }


    @GetMapping("/reactive-client/camel")
    public Mono<TestResponse> callReactiveFeignClientCamel() {
        return sampleReactiveLocalFeignClient.camelCall();
    }

    @GetMapping("/reactive-client/snake")
    public Mono<TestResponse> callReactiveFeignClientSnake() {
        return sampleReactiveLocalFeignClient.snakeCall();
    }


    @GetMapping("/client/post")
    public String callPostFeignClient() {
        return sampleLocalFeignClient.postCall(PostRequest.builder()
                .data("data")
                .dataMessage("message")
                .build());
    }

    @GetMapping("/client/post-map")
    public String callMapPostFeignClient() {
        return sampleLocalFeignClient.postMapCall(queryMapEncoder.encode(PostRequest.builder()
                .data("data")
                .dataMessage("message")
                .build()));
    }

    @GetMapping("/reactive-client/post")
    public Mono<String> callReactivePostFeignClient() {
        return sampleReactiveLocalFeignClient.postCall(PostRequest.builder()
                .data("data")
                .dataMessage("message")
                .build());
    }

    @GetMapping("/reactive-client/post-map")
    public Mono<String> callReactiveMapPostFeignClient() {
        return sampleReactiveLocalFeignClient.postMapCall(queryMapEncoder.encode(PostRequest.builder()
                .data("data")
                .dataMessage("message")
                .build()));
    }

    @GetMapping("/test-kotlin-response")
    public List<TestResponseKotlin> testKotlinResponse() {
        return sampleLocalFeignClient.testKotlinResponse();
    }
}
