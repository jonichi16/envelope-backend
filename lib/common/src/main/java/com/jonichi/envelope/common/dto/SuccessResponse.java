package com.jonichi.envelope.common.dto;

public class SuccessResponse<T> extends ApiResponse<T> {

    private final T data;

    public SuccessResponse(int code, String message, T data) {
        super(true, code, message);
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public T getError() {
        throw new UnsupportedOperationException("Error field is not supported");
    }

    @Override
    public T getErrorCode() {
        throw new UnsupportedOperationException("ErrorCode field is not supported");
    }

    public static <T> SuccessResponseBuilder<T> builder() {
        return new SuccessResponseBuilder<>();
    }

    public static class SuccessResponseBuilder<T> {
        private int code;
        private String message;
        private T data;

        public SuccessResponseBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public SuccessResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public SuccessResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public SuccessResponse<T> build() {
            return new SuccessResponse<>(code, message, data);
        }
    }
}