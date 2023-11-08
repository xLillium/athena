package com.nmotillon.athena;

import com.nmotillon.athena.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String DETAILS = "details";
    private static final String PATH = "path";
    private static final String VALIDATION_FAILED_MESSAGE = "Validation failed. See 'details' for more informations.";
    private static final String MALFORMED_REQUEST_BODY_MESSAGE = "The request body is missing or malformed.";

    private ResponseEntity<Object> buildErrorResponse(WebRequest request, HttpStatus status, String message, List<Map<String, String>> details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, status.value());
        body.put(ERROR, status.getReasonPhrase());
        body.put(MESSAGE, message);
        body.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        if (details != null) {
            body.put(DETAILS, details);
        }
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        return buildErrorResponse(request, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        return buildErrorResponse(request, HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> Map.of("field", fieldError.getField(), "message", Objects.requireNonNull(fieldError.getDefaultMessage())))
                .collect(Collectors.toList());

        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, VALIDATION_FAILED_MESSAGE, errors);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        return buildErrorResponse(request, HttpStatus.BAD_REQUEST, MALFORMED_REQUEST_BODY_MESSAGE, null);
    }
}
