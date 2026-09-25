package com.ms.inventory_service.services;

import com.ms.inventory_service.clients.OrderClient;
import com.ms.inventory_service.dtos.OrderDto;
import com.ms.inventory_service.dtos.ProductDto;
import com.ms.inventory_service.dtos.ProductReserveCreateResponseDto;
import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.entities.ProductReservesEntity;
import com.ms.inventory_service.exceptions.DataNotFoundException;
import com.ms.inventory_service.mapper.ProductMapper;
import com.ms.inventory_service.mapper.ProductReserveMapper;
import com.ms.inventory_service.repositories.ProductRepository;
import com.ms.inventory_service.repositories.ProductReserveRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;



    public ProductDto details(Long id){
        ProductEntity product=productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Product not found"));
        return ProductMapper.toDto(product);
    }

    @Transactional
    public void restoreAvailableQuantity(Long id,Integer quantity){
        productRepository.updateAvailableQuantity(id,quantity);
    }



}
