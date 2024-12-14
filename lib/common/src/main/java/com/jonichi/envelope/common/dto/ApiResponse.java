package com.jonichi.envelope.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiResponse<T> {

    private final boolean success;
    private final int code;
    private final String message;
    private final String timestamp;

    protected ApiResponse(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public abstract T getData();
    public abstract T getError();
    public abstract String getErrorCode();
}
