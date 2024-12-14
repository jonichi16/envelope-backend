package com.jonichi.envelope.common.advice;

import com.jonichi.envelope.common.constant.ErrorCode;
import com.jonichi.envelope.common.dto.ApiResponse;
import com.jonichi.envelope.common.dto.ErrorResponse;
import com.jonichi.envelope.common.exception.EnvelopeDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, List<String>>>> handleNotValidException(
            MethodArgumentNotValidException e
    ) {
        Map<String, List<String>> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();

            errorMap.computeIfAbsent(field, k -> new ArrayList<>()).add(errorMessage);
        });

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<Map<String, List<String>>> response = ErrorResponse
                .<Map<String, List<String>>>builder()
                .message("Validation error")
                .code(status.value())
                .errorCode(ErrorCode.NESTED_ERROR)
                .error(errorMap)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(EnvelopeDuplicateException.class)
    public ResponseEntity<ApiResponse<Void>> handleEnvelopeDuplicateException(EnvelopeDuplicateException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message(e.getMessage())
                .errorCode(ErrorCode.DUPLICATE)
                .build();

        return ResponseEntity.status(status).body(response);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message("Internal Server Error")
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
