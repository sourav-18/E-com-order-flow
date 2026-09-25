package com.ms.payment_service.services;

import com.ms.payment_service.dtos.PaymentStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String,Object>kafkaTemplate;

    void sendPaymentStatus(PaymentStatusDto paymentStatusDto){
        kafkaTemplate.send("payment-status-topic",paymentStatusDto.getPaymentId().toString(),paymentStatusDto);
    }
}
