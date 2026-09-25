package com.ms.payment_service.utils;

import com.ms.payment_service.entities.types.PaymentStatus;

public class PaymentUtil {
    public static  PaymentStatus getStatus(String status){
        return switch (status) {
            case "pending" -> PaymentStatus.valueOf("pending");
            case "success" -> PaymentStatus.valueOf("success");
            case "canceled" -> PaymentStatus.valueOf("canceled");
            case "failed" -> PaymentStatus.valueOf("failed");
            default -> throw new IllegalArgumentException("Invalid status");
        };
    }
}
