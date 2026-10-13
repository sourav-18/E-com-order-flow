package com.ms.payment_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.payment_service.entities.types.PaymentStatus;
import com.ms.payment_service.entities.types.PaymentType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Integer amount;
    private Long orderId;
    private PaymentType type;
    private PaymentStatus status;
    private String idempotencyKey;
    @JsonIgnore
    private Integer responseCode;
}
