package com.ms.payment_service.clients;

import com.ms.payment_service.dtos.OrderDto;
import com.ms.payment_service.dtos.ProductReserveDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/v1/products/reserved/{orderId}")
    ProductReserveDto reservedDetails(@PathVariable("orderId") Long orderId);
}
