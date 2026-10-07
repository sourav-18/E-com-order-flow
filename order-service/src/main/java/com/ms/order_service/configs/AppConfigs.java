package com.ms.order_service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class AppConfigs {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
