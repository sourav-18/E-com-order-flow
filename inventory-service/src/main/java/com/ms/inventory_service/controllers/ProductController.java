package com.ms.inventory_service.controllers;

import com.ms.inventory_service.dtos.*;
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

    @PostMapping("/reserved/{orderId}")
    public ResponseEntity<ApiResponseDto<ProductReserveCreateResponseDto>> reserved(@PathVariable("orderId") Long orderId){
        ProductReserveCreateResponseDto reserved = productReserveService.reserved(orderId);
        ApiResponseDto<ProductReserveCreateResponseDto>apiResponse=new ApiResponseDto<>(201,"Product Reserved successfully",reserved);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping("/reserved/{orderId}")
    public ProductReserveDto reservedDetails(@PathVariable("orderId") Long orderId){
        return productReserveService.details(orderId);
    }


}
