package com.ms.payment_service.exceptins;


import com.ms.payment_service.dtos.ApiErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ApiErrorResponseDto> handleInvalidArgumentException(InvalidArgumentException ex) {
        return ResponseEntity.status(400)
                .body(new ApiErrorResponseDto(400,ex.getMessage()));
    }
}
