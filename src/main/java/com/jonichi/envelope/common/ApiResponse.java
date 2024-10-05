package com.jonichi.envelope.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public abstract class ApiResponse<T> {

    private Integer code;
    private String message;
    @Builder.Default
    private String timestamp = Instant.now().toString();

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"status", "code", "message", "data", "timestamp"})
    public static class Success<T> extends ApiResponse<T> {
        @Builder.Default
        private final String status = "success";
        private T data;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"status", "code", "message", "errorCode", "error", "timestamp"})
    public static class Error<T> extends ApiResponse<T> {
        @Builder.Default
        private final String status = "error";
        private String errorCode;
        private T error;
    }

}