package com.jonichi.envelope.common.response;

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
    @JsonPropertyOrder({"success", "code", "message", "data", "timestamp"})
    public static class Success<T> extends ApiResponse<T> {
        @Builder.Default
        private final boolean success = true;
        private T data;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({"success", "code", "message", "errorCode", "error", "timestamp"})
    public static class Error<T> extends ApiResponse<T> {
        @Builder.Default
        private final boolean success = false;
        private String errorCode;
        private T error;
    }

}