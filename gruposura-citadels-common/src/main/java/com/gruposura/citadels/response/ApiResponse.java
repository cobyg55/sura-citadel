package com.gruposura.citadels.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String status;
    private String timestamp;
    private String message;
    private String code;

    public ApiResponse() {

    }

    public ApiResponse(String status, String timestamp, String message, String code) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.code = code;
    }
}
