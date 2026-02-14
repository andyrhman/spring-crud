package com.myspring.spring_crud.exception;

import java.time.Instant;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<ApiError> handleConflict(ConflictException ex, HttpServletRequest req) {
                ApiError body = new ApiError(
                                Instant.now(),
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase(),
                                ex.getMessage(),
                                req.getRequestURI());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
                ApiError body = new ApiError(
                                Instant.now(),
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                ex.getMessage(),
                                req.getRequestURI());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
                ApiError body = new ApiError(
                                Instant.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                ex.getMessage(),
                                req.getRequestURI());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }

        @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidation(
                        org.springframework.web.bind.MethodArgumentNotValidException ex,
                        HttpServletRequest req) {
                String message = ex.getBindingResult().getFieldErrors().stream()
                                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                .reduce((a, b) -> a + "; " + b)
                                .orElse("Validation failed");
                ApiError body = new ApiError(
                                Instant.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                message,
                                req.getRequestURI());
                return ResponseEntity.badRequest().body(body);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
                ApiError body = new ApiError(
                                Instant.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                "Unexpected error",
                                req.getRequestURI());
                ex.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
}
