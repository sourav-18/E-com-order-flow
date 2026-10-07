package com.ms.api_gateway.dots;

import lombok.Getter;

@Getter
public class ApiErrorResponseDto  extends BaseApiResponseDto{
    public ApiErrorResponseDto(Integer status, String message) {
        super(false, status, message);
    }
}
