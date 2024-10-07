package com.jonichi.envelope.advice;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.jonichi.envelope.common.response.ApiResponse;
import com.jonichi.envelope.common.response.FieldError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

//    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<?> handleValidationExceptions(ConstraintViolationException e) {
//
//        // Grouping violations by the propertyPath and collecting error messages
//        Map<String, List<String>> groupedErrors = e.getConstraintViolations().stream()
//                .collect(Collectors.groupingBy(
//                        violation -> violation.getPropertyPath().toString(),
//                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
//                ));
//
//        // Creating a list of FieldError records
//        List<FieldError> errorResponse = groupedErrors.entrySet().stream()
//                .map(entry -> new FieldError(entry.getKey(), entry.getValue()))
//                .collect(Collectors.toList());
//
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        ApiResponse<List<FieldError>> response = ApiResponse.Error.<List<FieldError>>builder()
//                .message("Invalid request.")
//                .code(status.value())
//                .error(errorResponse)
//                .build();
//
//        return ResponseEntity.status(status).body(response);
//
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<?> handleNotValidField(UnrecognizedPropertyException e) {

        System.out.println(e.getMessage());
        System.out.println(e.getMessageSuffix());
        System.out.println(e.getOriginalMessage());
        System.out.println(e.getClass());
        System.out.println(e.getKnownPropertyIds());
        System.out.println(e.getPath());
        System.out.println(e.getPathReference());
        System.out.println(e.getReferringClass());
        System.out.println();


        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body("ERROR");

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleDatabaseException(ConstraintViolationException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        System.out.println(e.getConstraintViolations());


        // Safely extract error message
        String errorMessage = Optional.ofNullable(e.getCause())
                .map(Throwable::getCause)
                .map(Throwable::getMessage)
                .orElse("Unknown database error");

        String duplicateModule = extractDuplicateModule(errorMessage);


        // Construct the error message
        String message = duplicateModule + " already exists.";
        ApiResponse<Void> response = ApiResponse.Error.<Void>builder()
                .message(message)
                .code(status.value())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    // Use regex to robustly extract the duplicate module name
    private String extractDuplicateModule(String errorMessage) {
        Pattern pattern = Pattern.compile("'([^']*)'");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Record";
        }
    }
}
