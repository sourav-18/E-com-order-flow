package com.ms.inventory_service.controllers;

import com.ms.inventory_service.dtos.ApiErrorResponseDto;
import com.ms.inventory_service.dtos.ApiResponseDto;
import com.ms.inventory_service.dtos.ProductDto;
import com.ms.inventory_service.services.ProductReserveService;
import com.ms.inventory_service.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductReserveService productReserveService;

    @GetMapping("/{id}")
    public ProductDto details(@PathVariable("id") Long id){
        return productService.details(id);
    }

    @GetMapping("/reserved/{orderId}")
    public Object reserved(@PathVariable("orderId") Long orderId){
       return productReserveService.reserved(orderId);
    }

}
