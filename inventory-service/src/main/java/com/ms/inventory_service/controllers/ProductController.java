package com.ms.inventory_service.controllers;

import com.ms.inventory_service.dtos.ApiErrorResponseDto;
import com.ms.inventory_service.dtos.ApiResponseDto;
import com.ms.inventory_service.dtos.ProductDto;
import com.ms.inventory_service.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProductDto>> details(@PathVariable("id") Long id){
        ProductDto product=productService.details(id);
        ApiResponseDto<ProductDto> apiResponse =
                new ApiResponseDto<>(HttpStatus.CREATED.value(), "Product details fetch successfully",product);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

}
