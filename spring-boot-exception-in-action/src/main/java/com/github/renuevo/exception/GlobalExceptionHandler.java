package com.github.renuevo.exception;

import com.github.renuevo.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Handle Exception {}", e.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.REST_CONTROLLER_VALUE);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
