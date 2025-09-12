package com.banking.eagle.exceptions;

import com.banking.eagle.api.ProblemDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetails> handleResponseStatusException(ResponseStatusException ex) {
        ProblemDetails problem = ProblemDetails.builder()
                .status(ex.getStatusCode().value())
                .errorCode("BUSINESS_ERROR")
                .message(ex.getReason())
                .traceId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(ex.getStatusCode()).body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        ProblemDetails problem = ProblemDetails.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode("VALIDATION_ERROR")
                .message(message)
                .traceId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.badRequest().body(problem);
    }

    @ExceptionHandler(Exception.class) // catch-all
    public ResponseEntity<ProblemDetails> handleGenericException(Exception ex) {
        ProblemDetails problem = ProblemDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode("INTERNAL_ERROR")
                .message("Unexpected error occurred")
                .traceId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }
}



