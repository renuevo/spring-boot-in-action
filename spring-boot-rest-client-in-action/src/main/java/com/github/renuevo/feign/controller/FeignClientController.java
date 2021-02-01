package com.github.renuevo.feign.controller;


import com.github.renuevo.common.NaverProperty;
import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import com.github.renuevo.feign.client.*;
import feign.QueryMapEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeignClientController {

    private final SampleFeignClient sampleFeignClient;
    private final SampleBuildFeignClient sampleBuildFeignClient;
    private final SampleCircuitFeignClient sampleCircuitFeignClient;
    private final SampleReactiveFeignClient sampleReactiveFeignClient;
    private final SampleErrorFeignClient sampleErrorFeignClient;
    private final SampleErrorReactiveFeignClient sampleErrorReactiveFeignClient;
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

    @GetMapping("/client/error/{code}")
    public void callErrorClient(@PathVariable("code") Integer code) {
        switch (code) {
            case 400:
                sampleErrorFeignClient.get400();
                break;
            case 500:
            default:
                sampleErrorFeignClient.get500();
                break;
        }
    }

    @GetMapping("/reactive-client/error/{code}")
    public Mono<String> callErrorReactiveClient(@PathVariable("code") Integer code) {
        Mono<String> callClient;
        switch (code) {
            case 400:
                callClient = sampleErrorReactiveFeignClient.get400();
                break;
            case 500:
            default:
                callClient = sampleErrorReactiveFeignClient.get500();
                break;
        }
        return callClient;
    }

}
