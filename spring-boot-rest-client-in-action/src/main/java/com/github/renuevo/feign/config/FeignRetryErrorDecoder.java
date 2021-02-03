package com.github.renuevo.feign.config;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.time.LocalDate;

@Slf4j
public class FeignRetryErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("요청에 실패 하였습니다 : {}", response.body());

        switch (response.status()) {
            case 400:
                log.error("다시 요청 합니다");
                return new RetryableException(response.status(),"error", response.request().httpMethod(), Date.valueOf(LocalDate.now()), response.request());
            case 500:
            default:
                return new Exception(response.body().toString());
        }
    }
}
