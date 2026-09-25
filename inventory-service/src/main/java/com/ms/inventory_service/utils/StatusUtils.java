package com.ms.inventory_service.utils;

import com.ms.inventory_service.dtos.PaymentStatus;
import com.ms.inventory_service.entities.types.ProductReservesStatusType;

public class StatusUtils {
    public static ProductReservesStatusType PaymentStatusToProductReservesStatus(PaymentStatus paymentStatus){
        return switch (paymentStatus){
            case pending -> ProductReservesStatusType.pending;
            case success -> ProductReservesStatusType.success;
            case failed,canceled -> ProductReservesStatusType.cancel;
            default ->  throw new IllegalArgumentException("Invalid status");
        };
    }
}
