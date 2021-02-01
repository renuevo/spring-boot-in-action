package com.github.renuevo.feign.config;

import lombok.extern.slf4j.Slf4j;
import reactivefeign.client.ReactiveHttpResponse;
import reactivefeign.client.statushandler.ReactiveStatusHandler;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveFeignErrorDecoder implements ReactiveStatusHandler {
    @Override
    public boolean shouldHandle(int status) {
        return status >= 400;
    }

    @Override
    public Mono<? extends Throwable> decode(String methodTag, ReactiveHttpResponse<?> response) {
        log.error("요청에 실패 하였습니다 : {}", response.body());
        return Mono.error(new Exception(response.body().toString()));
    }
}
