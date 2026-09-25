package com.ms.payment_service.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic paymentEventTopic(){
        return new NewTopic("payment-status-topic",3,(short) 1);
    }
}
