package com.ms.inventory_service.services;

import com.ms.inventory_service.dtos.ProductDto;
import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.exceptions.DataNotFoundException;
import com.ms.inventory_service.mapper.ProductMapper;
import com.ms.inventory_service.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto details(Long id){
        ProductEntity product=productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Product not found"));
        return ProductMapper.toDto(product);
    }
}
