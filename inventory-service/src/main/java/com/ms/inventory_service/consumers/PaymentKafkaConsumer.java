package com.ms.inventory_service.consumers;

import com.ms.inventory_service.dtos.PaymentStatusDto;
import com.ms.inventory_service.services.ProductReserveService;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

    private final ProductReserveService productReserveService;
    private final ObjectMapper objectMapper=new ObjectMapper();

    @KafkaListener(topics = "payment-status-topic")
    public void handlePaymentStatus(ConsumerRecord<String, String>record){
        PaymentStatusDto paymentStatusDto = objectMapper.readValue(record.value(), PaymentStatusDto.class);
        productReserveService.statusUpdate(paymentStatusDto);
    }
}
