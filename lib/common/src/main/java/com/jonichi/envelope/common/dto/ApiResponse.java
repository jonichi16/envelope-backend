package com.jonichi.envelope.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Abstract class representing a generic API response.
 *
 * @param <T> the type of data or error response in the API response
 */
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

    /**
     * A class representing a successful API response.
     *
     * @param <T> the type of data being returned in the success response
     */
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

    /**
     * A class representing an error API response.
     *
     * @param <T> the type of error details being returned in the error response
     */
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