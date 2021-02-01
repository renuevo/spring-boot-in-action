package com.github.renuevo.feign.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("요청에 실패 하였습니다 : {}", response.body());

        switch (response.status()) {
            case 400:
            case 500:
            default:
                return new Exception(response.body().toString());
        }
    }
}
