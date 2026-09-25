package com.ms.order_service.consumers;

import com.ms.order_service.dtos.PaymentStatusDto;
import com.ms.order_service.entities.types.OrderStatusType;
import com.ms.order_service.services.OrderService;
import com.ms.order_service.utils.StatusUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-status-topic")
    public void handlePaymentStatus(ConsumerRecord<String, PaymentStatusDto>record){
        PaymentStatusDto value = record.value();
        OrderStatusType status= StatusUtils.PaymentStatusToOrderStatus(value.getStatus());
        orderService.statusUpdate(value.getOrderId(),status);
    }
}
