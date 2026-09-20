package com.ms.order_service.dtos;

import lombok.Getter;

@Getter
public class ApiErrorResponseDto  extends BaseApiResponseDto{
    public ApiErrorResponseDto(Integer status, String message) {
        super(false, status, message);
    }
}
