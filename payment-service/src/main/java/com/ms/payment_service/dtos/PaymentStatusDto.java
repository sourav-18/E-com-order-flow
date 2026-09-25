package com.ms.payment_service.dtos;

import com.ms.payment_service.entities.types.PaymentStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusDto {
    private Long paymentId;
    private PaymentStatus status;
    private Long orderId;
}
