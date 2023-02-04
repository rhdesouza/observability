package com.example.observability.webapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5515151541L;

    public BusinessException() {
        super();
    }

    public BusinessException(String exception) {
        super(exception);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
