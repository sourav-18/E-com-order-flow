package com.ms.payment_service.services;

import com.ms.payment_service.clients.InventoryClient;
import com.ms.payment_service.clients.OrderClient;
import com.ms.payment_service.dtos.*;
import com.ms.payment_service.entities.PaymentEntity;
import com.ms.payment_service.entities.types.PaymentStatus;
import com.ms.payment_service.mappers.PaymentMapper;
import com.ms.payment_service.repositories.PaymentRepository;
import com.ms.payment_service.utils.PaymentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InventoryClient inventoryClient;
    private final KafkaService kafkaService;

    @Transactional
    public PaymentResponseDto payment(Long orderId) {
        ProductReserveDto productReserveDetails = inventoryClient.reservedDetails(orderId);
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId).orElse(null);
        if (paymentEntity != null) {
            return PaymentMapper.toDto(paymentEntity, 200);
        }
        PaymentEntity newPayment = PaymentMapper.toEntity(orderId, productReserveDetails.getTotalAmount(), orderId.toString());
        paymentRepository.save(newPayment);
        return PaymentMapper.toDto(newPayment, 201);
    }

    @Transactional
    public void updateStatus(PaymentStatusUpdateRequest body) {
        Long paymentId = Long.valueOf(body.getPaymentId());
        PaymentStatus status = PaymentUtil.getStatus(body.getStatus());
        Integer updated = paymentRepository.updateStatus(paymentId, status, PaymentStatus.pending);
//        if (updated == 0) return;
        PaymentEntity paymentEntity = paymentRepository.findById(paymentId).orElseThrow();
        kafkaService.sendPaymentStatus(
                PaymentStatusDto.builder().
                        paymentId(paymentId)
                        .status(status)
                        .orderId(paymentEntity.getOrderId())
                        .build()
        );
        System.out.println(body.getStatus());
    }
}
