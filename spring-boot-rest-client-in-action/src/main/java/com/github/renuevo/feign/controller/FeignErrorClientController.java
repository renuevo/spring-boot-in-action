package com.github.renuevo.feign.controller;

import com.github.renuevo.feign.client.error.*;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("error")
public class FeignErrorClientController {

    private final SampleErrorFeignClient sampleErrorFeignClient;
    private final SampleErrorReactiveFeignClient sampleErrorReactiveFeignClient;
    private final SampleErrorRetryFeignClient sampleErrorRetryFeignClient;
    private final SampleErrorRetryReactiveFeignClient sampleErrorRetryReactiveFeignClient;
    private final SampleErrorReactiveFeignCustomClient sampleErrorReactiveFeignCustomClient;


    @GetMapping("/client/{code}")
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

    @GetMapping("/reactive-client/{code}")
    public Mono<String> callErrorReactiveClient(@PathVariable("code") Integer code) {
        switch (code) {
            case 400:
                return sampleErrorReactiveFeignClient.get400();
            case 500:
            default:
                return sampleErrorReactiveFeignClient.get500();
        }
    }

    @GetMapping("/retry-client/{code}")
    public void callErrorRetryClient(@PathVariable("code") Integer code) {
        switch (code) {
            case 400:
                sampleErrorRetryFeignClient.get400();
                break;
            case 500:
            default:
                sampleErrorRetryFeignClient.get500();
                break;
        }
    }

    @GetMapping("/reactive-retry-client/{code}")
    public Mono<String> callErrorReactiveRetryClient(@PathVariable("code") Integer code) {
        switch (code) {
            case 400:
                return sampleErrorRetryReactiveFeignClient.get400().retry(3, e -> e instanceof RetryableException);
            case 500:
            default:
                return sampleErrorRetryReactiveFeignClient.get500().retry(3, e -> e instanceof RetryableException);
        }
    }


    @GetMapping("/custom-reactive-client/{code}")
    public Mono<String> callErrorCustomReactiveClient(@PathVariable("code") Integer code) {
        switch (code) {
            case 400:
                return sampleErrorReactiveFeignCustomClient.get400();
            case 500:
            default:
                return sampleErrorReactiveFeignCustomClient.get500();
        }
    }


}
