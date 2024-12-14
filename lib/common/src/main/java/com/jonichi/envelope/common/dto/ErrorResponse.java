package com.jonichi.envelope.common.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"success", "code", "message", "errorCode", "error", "timestamp"})
public class ErrorResponse<T> extends ApiResponse<T> {

    private final T error;
    private final String errorCode;

    public ErrorResponse(int code, String message, String errorCode, T error) {
        super(false, code, message);
        this.error = error;
        this.errorCode = errorCode;
    }

    @Override
    public T getData() {
        return null;
    }

    @Override
    public T getError() {
        return error;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    public static <T> ErrorResponseBuilder<T> builder() {
        return new ErrorResponseBuilder<>();
    }

    public static class ErrorResponseBuilder<T> {
        private int code;
        private String message;
        private T error;
        private String errorCode;

        public ErrorResponseBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ErrorResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder<T> error(T error) {
            this.error = error;
            return this;
        }

        public ErrorResponseBuilder<T> errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ErrorResponse<T> build() {
            return new ErrorResponse<>(code, message, errorCode, error);
        }
    }
}
