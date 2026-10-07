package com.ms.order_service.exceptions;

import lombok.Getter;

@Getter
public class FeignClientException extends RuntimeException {
    private final int status;
    public FeignClientException(String message, int status) {
        super(message);
        this.status = status;
    }
}
