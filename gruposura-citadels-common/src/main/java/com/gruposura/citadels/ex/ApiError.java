package com.gruposura.citadels.ex;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String code;

    public ApiError() {

    }

    public ApiError(HttpStatus status, String message, String code) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.code = code;
    }

    public ApiError(HttpStatus status, String message, String code, Throwable ex) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.code = code;
    }
}
