package com.ms.inventory_service.clients;

import com.ms.inventory_service.dtos.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {
    @GetMapping("/api/v1/orders/{id}")
    OrderDto details(@PathVariable("id") Long id);
}
