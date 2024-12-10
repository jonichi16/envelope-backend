package com.jonichi.envelope.common.dto;

import java.time.Instant;

public abstract class BaseResponse {

    private final boolean success;
    private int code;
    private String message;
    private final String timestamp;

    public BaseResponse(boolean success) {
        this.success = success;
        this.timestamp = Instant.now().toString();
    }

    public BaseResponse(
            boolean success,
            int code,
            String message
    ) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
