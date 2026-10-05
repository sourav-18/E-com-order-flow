package com.ms.inventory_service.controllers;

import com.ms.inventory_service.dtos.*;
import com.ms.inventory_service.services.ProductReserveService;
import com.ms.inventory_service.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ProductDto details(HttpServletRequest request, @PathVariable("id") Long id){
        System.out.println(request.getHeader("user-id"));
        return productService.details(id);
    }

    @PostMapping("/reserved/{orderId}")
    public ResponseEntity<ApiResponseDto<ProductReserveCreateResponseDto>> reserved(HttpServletRequest request,@PathVariable("orderId") Long orderId){
        Long userId = Long.valueOf(request.getHeader("user-id"));
        ProductReserveCreateResponseDto reserved = productReserveService.reserved(orderId,userId);
        ApiResponseDto<ProductReserveCreateResponseDto>apiResponse=new ApiResponseDto<>(201,"Product Reserved successfully",reserved);
        return ResponseEntity.status(201).body(apiResponse);
    }

    @GetMapping("/reserved/{orderId}")
    public ProductReserveDto reservedDetails(@PathVariable("orderId") Long orderId){
        return productReserveService.details(orderId);
    }


}
