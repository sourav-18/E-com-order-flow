package com.ms.payment_service.mappers;

import com.ms.payment_service.dtos.PaymentResponseDto;
import com.ms.payment_service.entities.PaymentEntity;
import com.ms.payment_service.entities.types.PaymentStatus;
import com.ms.payment_service.entities.types.PaymentType;

public class PaymentMapper {

    public static PaymentEntity toEntity(Long orderId,Integer amount,String idempotencyKey){
        return PaymentEntity.builder()
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.pending)
                .type(PaymentType.order)
                .idempotencyKey(idempotencyKey)
                .build();
    }

    public static PaymentResponseDto toDto(PaymentEntity payment,Integer responseCode){
       return PaymentResponseDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .idempotencyKey(payment.getIdempotencyKey())
                .responseCode(responseCode)
                .type(payment.getType())
                .status(payment.getStatus())
                .build();
    }

}
