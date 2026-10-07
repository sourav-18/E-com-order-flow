package com.ms.api_gateway.dots;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseApiResponseDto {
    private final Boolean success;
    private final Integer status;
    private final String message;
    private final LocalDateTime timestamp=LocalDateTime.now();

    public BaseApiResponseDto(Boolean success, Integer status, String message) {
        this.success = success;
        this.status = status;
        this.message = message;
    }
}

