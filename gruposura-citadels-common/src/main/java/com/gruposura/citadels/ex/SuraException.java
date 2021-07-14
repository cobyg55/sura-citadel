package com.gruposura.citadels.ex;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SuraException extends RuntimeException {
    private String code;
    private String message;
    private HttpStatus httpStatus;

    public SuraException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
