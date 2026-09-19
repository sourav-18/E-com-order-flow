package com.ms.inventory_service.dtos;


import lombok.Getter;

@Getter
public class ApiResponseDto<T> extends BaseApiResponseDto{
    private T data;
    public ApiResponseDto(Integer status, String message,T data) {
        super(true, status, message);
        this.data=data;
    }
}
