package com.yog.electronicstore.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yog.electronicstore.config.ApiResponse;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
    public class GlobalExceptionHandling {
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandling(ResourceNotFoundException rs) {
            String msg = rs.getMessage();
            ApiResponse response = new ApiResponse();
            return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandling(
                MethodArgumentNotValidException mx) {
            Map<String, String> map = new HashMap<>();
            mx.getBindingResult().getAllErrors().forEach((error) -> {
                String field = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                                map.put(field, message);
            });
            return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);

        }
        @ExceptionHandler(ApiException.class)
        public ResponseEntity<ApiResponse> ApiExceptionHandling(ApiException rs) {
            String msg = rs.getMessage();

            ApiResponse response = new ApiResponse();


            return new ResponseEntity<ApiResponse>(response, HttpStatus.BAD_REQUEST);
        }

    }

