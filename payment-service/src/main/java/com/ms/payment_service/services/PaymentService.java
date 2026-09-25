package com.ms.payment_service.services;

import com.ms.payment_service.clients.InventoryClient;
import com.ms.payment_service.clients.OrderClient;
import com.ms.payment_service.dtos.OrderDto;
import com.ms.payment_service.dtos.PaymentResponseDto;
import com.ms.payment_service.dtos.ProductReserveDto;
import com.ms.payment_service.entities.PaymentEntity;
import com.ms.payment_service.mappers.PaymentMapper;
import com.ms.payment_service.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InventoryClient inventoryClient;

    @Transactional
   public PaymentResponseDto payment(Long orderId){
        ProductReserveDto productReserveDetails = inventoryClient.reservedDetails(orderId);
        PaymentEntity paymentEntity = paymentRepository.findByOrderId(orderId).orElse(null);
        if(paymentEntity!=null){
            return PaymentMapper.toDto(paymentEntity,200);
        }
        PaymentEntity newPayment= PaymentMapper.toEntity(orderId,productReserveDetails.getTotalAmount(),orderId.toString());
        paymentRepository.save(newPayment);
        return PaymentMapper.toDto(newPayment,201);
    }
}
