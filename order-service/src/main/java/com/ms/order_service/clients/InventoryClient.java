package com.ms.order_service.clients;

import com.ms.order_service.dtos.ApiResponseDto;
import com.ms.order_service.dtos.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/v1/products/{id}")
   ProductDto getProductDetails(@PathVariable("id") Long productId);
}
