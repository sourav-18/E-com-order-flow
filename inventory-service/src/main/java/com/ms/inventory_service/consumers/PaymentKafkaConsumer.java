package com.ms.inventory_service.consumers;

import com.ms.inventory_service.dtos.PaymentStatusDto;
import com.ms.inventory_service.services.ProductReserveService;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

    private final ProductReserveService productReserveService;

    @KafkaListener(topics = "payment-status-topic")
    public void handlePaymentStatus(ConsumerRecord<String, PaymentStatusDto>record){
        PaymentStatusDto value = record.value();
        productReserveService.statusUpdate(value);
    }
}
