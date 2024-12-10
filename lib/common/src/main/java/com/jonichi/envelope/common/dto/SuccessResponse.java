package com.jonichi.envelope.common.dto;

public class SuccessResponse<T> extends BaseResponse {

    private T data;

    public SuccessResponse(int code, String message, T data) {
        super(true, code, message);
        this.data = data;
    }

    public SuccessResponse() {
        super(true);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
