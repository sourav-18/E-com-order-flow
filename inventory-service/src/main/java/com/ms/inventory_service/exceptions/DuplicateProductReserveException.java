package com.ms.inventory_service.exceptions;

public class DuplicateProductReserveException extends RuntimeException {
    public DuplicateProductReserveException(String message) {
        super(message);
    }
}
