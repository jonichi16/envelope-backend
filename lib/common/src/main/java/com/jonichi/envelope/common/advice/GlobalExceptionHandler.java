package com.jonichi.envelope.common.advice;

import com.jonichi.envelope.common.constant.ErrorCode;
import com.jonichi.envelope.common.dto.ApiResponse;
import com.jonichi.envelope.common.dto.ErrorResponse;
import com.jonichi.envelope.common.exception.EnvelopeDuplicateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Global exception handler for the Envelope application.
 *
 * <p>This class handles various types of exceptions thrown throughout the application
 * and provides appropriate HTTP responses to the client. It includes handlers for validation
 * errors, custom application exceptions, and general exceptions.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link MethodArgumentNotValidException} exceptions.
     *
     * <p>These exceptions occur when validation constraints on request parameters are violated.
     * The response contains a detailed map of field-specific error messages.</p>
     *
     * @param e the exception thrown due to validation errors
     * @return a {@link ResponseEntity} with a structured validation error response
     */
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

    /**
     * Handles {@link EnvelopeDuplicateException} exceptions.
     *
     * <p>These exceptions occur when an attempt is made to create a duplicate resource,
     * such as a user or entity that already exists.</p>
     *
     * @param e the exception thrown when a duplicate resource is detected
     * @return a {@link ResponseEntity} with a duplicate error response
     */
    @ExceptionHandler(EnvelopeDuplicateException.class)
    public ResponseEntity<ApiResponse<Void>> handleEnvelopeDuplicateException(
            EnvelopeDuplicateException e
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message(e.getMessage())
                .errorCode(ErrorCode.DUPLICATE)
                .build();

        return ResponseEntity.status(status).body(response);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ) {
        String errorMessage = e.getMessage();
        String message = errorMessage;

        if (errorMessage.contains("Unrecognized field")) {
            int startIdx = errorMessage.indexOf("\"") + 1;
            int endIdx = errorMessage.indexOf("\"", startIdx);
            String field = errorMessage.substring(startIdx, endIdx);
            message = field + " is not allowed";
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message(message)
                .errorCode(ErrorCode.NOT_ALLOWED)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handles {@link NoResourceFoundException} and maps it to a 404 Not Found response.
     *
     * <p>This method constructs an error response using {@link ErrorResponse} with a specific
     * error code ({@link ErrorCode#NOT_FOUND}). It ensures that clients receive a structured
     * error response with meaningful details when a resource cannot be found.</p>
     *
     * @param e the {@link NoResourceFoundException} thrown when a requested resource is not found
     * @return a {@link ResponseEntity} containing a structured error response
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoResourceFoundException(
            NoResourceFoundException e
    ) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message(e.getMessage())
                .errorCode(ErrorCode.NOT_FOUND)
                .build();

        return ResponseEntity.status(status).body(response);
    }

    /**
     * Handles all uncaught exceptions in the application.
     *
     * <p>This is a fallback handler that catches any exceptions not explicitly handled by other
     * methods. It returns a generic "Internal Server Error" response.</p>
     *
     * @param e the exception that was not explicitly handled
     * @return a {@link ResponseEntity} with an internal server error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAll(Exception e) {
        System.out.println(e.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponse<Void> response = ErrorResponse.<Void>builder()
                .code(status.value())
                .message("Internal Server Error")
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
