package com.github.renuevo.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final String errorCode;
}
