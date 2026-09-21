package com.ms.order_service.exceptions;

import com.ms.order_service.dtos.ApiErrorResponseDto;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, List<String>> errorsMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                error -> Objects.requireNonNullElse(
                                        error.getDefaultMessage(),
                                        "Invalid value"
                                ),
                                Collectors.toList()
                        )
                ));
        Map.Entry<String,List<String>> entry=errorsMessages.entrySet().iterator().next();
        String errorsMessage=entry.getKey()+" "+String.join(" and ",entry.getValue());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponseDto(HttpStatus.BAD_REQUEST.value(),errorsMessage));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(FeignException ex) {
        return ResponseEntity.status(400)
                .body(new ApiErrorResponseDto(400,ex.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(DataNotFoundException ex) {
        return ResponseEntity.status(400)
                .body(new ApiErrorResponseDto(400,ex.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationException(InsufficientStockException ex) {
        return ResponseEntity.status(400)
                .body(new ApiErrorResponseDto(400,ex.getMessage()));
    }
}
