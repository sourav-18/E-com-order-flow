package com.ms.payment_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatusUpdateRequest {
    private String paymentId;
    private String status;
}
