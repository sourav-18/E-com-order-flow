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
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayDeque;
import java.util.*;

@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper=new ObjectMapper();

    @KafkaListener(topics = "payment-status-topic")
    public void handlePaymentStatus(ConsumerRecord<String, String>record){
        PaymentStatusDto paymentStatusDto = objectMapper.readValue(record.value(), PaymentStatusDto.class);
        OrderStatusType status= StatusUtils.PaymentStatusToOrderStatus(paymentStatusDto.getStatus());
        orderService.statusUpdate(paymentStatusDto.getOrderId(),status);
    }
}
