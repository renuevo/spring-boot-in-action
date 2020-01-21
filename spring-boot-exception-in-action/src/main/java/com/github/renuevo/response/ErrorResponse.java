package com.github.renuevo.response;

import com.github.renuevo.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private List<FieldError> errors;
    private String code;

    @Getter
    @NoArgsConstructor
    public static  class FieldError{
        private String field;
        private String value;
        private String reason;
    }

    public ErrorResponse(final ErrorCode code){
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

}
