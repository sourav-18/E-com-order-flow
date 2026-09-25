package com.ms.order_service.utils;

import com.ms.order_service.dtos.PaymentStatus;
import com.ms.order_service.entities.types.OrderStatusType;

public class StatusUtils {
    public static OrderStatusType PaymentStatusToOrderStatus(PaymentStatus paymentStatus){
        return switch (paymentStatus){
            case pending -> OrderStatusType.pending;
            case success -> OrderStatusType.completed;
            case failed,canceled -> OrderStatusType.failed;
            default ->  throw new IllegalArgumentException("Invalid status");
        };
    }
}
