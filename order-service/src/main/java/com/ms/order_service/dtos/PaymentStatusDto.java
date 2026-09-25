package com.ms.order_service.dtos;


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
