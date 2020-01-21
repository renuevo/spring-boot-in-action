package com.github.renuevo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    REST_CONTROLLER_VALUE(400, "V001", "Invalid Input Value");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
