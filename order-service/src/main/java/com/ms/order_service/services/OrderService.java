package com.ms.order_service.services;

import com.ms.order_service.clients.InventoryClient;
import com.ms.order_service.dtos.OrderCreateRequestDto;
import com.ms.order_service.dtos.ProductDto;
import com.ms.order_service.entities.OrderEntity;
import com.ms.order_service.mapper.OrderMapper;
import com.ms.order_service.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.swing.*;

@Service
@AllArgsConstructor
public class OrderService {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;

    public Object create(OrderCreateRequestDto body){
        ProductDto productDetails = inventoryClient.getProductDetails(body.getProductId());
        if(productDetails.getAvailableQuantity()< body.getQuantity()){
            throw new RuntimeException();
        }
        OrderEntity orderEntity = orderRepository.findByIdempotencyKey(body.getIdempotencyKey()).orElse(null);
        if(orderEntity!=null){
            return orderEntity;
        }

        OrderEntity newOrder = OrderMapper.toEntity(body, productDetails);

        OrderEntity save = orderRepository.save(newOrder);

        return null;

    }
}
