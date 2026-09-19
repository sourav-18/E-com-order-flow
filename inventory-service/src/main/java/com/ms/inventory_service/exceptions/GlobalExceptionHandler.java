package com.ms.inventory_service.exceptions;

import com.ms.inventory_service.dtos.ApiErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(DataNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new ApiErrorResponseDto(404,ex.getMessage()));
    }
}
