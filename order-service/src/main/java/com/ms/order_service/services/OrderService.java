package com.ms.order_service.services;

import com.ms.order_service.clients.InventoryClient;
import com.ms.order_service.dtos.OrderCreateResponseDto;
import com.ms.order_service.dtos.OrderCreateRequestDto;
import com.ms.order_service.dtos.OrderDto;
import com.ms.order_service.dtos.ProductDto;
import com.ms.order_service.entities.OrderEntity;
import com.ms.order_service.entities.types.OrderStatusType;
import com.ms.order_service.exceptions.DataNotFoundException;
import com.ms.order_service.exceptions.InsufficientStockException;
import com.ms.order_service.mapper.OrderMapper;
import com.ms.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    @Value("${inventory.reserved.api}")
    private String nextApiUrl;

    public OrderCreateResponseDto create(OrderCreateRequestDto body, Long userId) {
        OrderEntity orderEntity = orderRepository.findByIdempotencyKey(body.getIdempotencyKey()).orElse(null);
        if (orderEntity != null) {
            return OrderMapper.toDto(orderEntity, 200, nextApiUrl);
        }

        ProductDto productDetails = inventoryClient.getProductDetails(body.getProductId());
        if (productDetails == null) {
            throw new DataNotFoundException("Product not found");
        }

        if (productDetails.getAvailableQuantity() < body.getQuantity()) {
            throw new InsufficientStockException("Product stock is not available");
        }

        OrderEntity newOrder = OrderMapper.toEntity(body, userId, productDetails);
        orderRepository.save(newOrder);

        return OrderMapper.toDto(newOrder, 201, nextApiUrl);
    }

    public OrderDto details(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("order id not found"));
        return OrderMapper.toDto(order);
    }

    @Transactional
    public void statusUpdate(Long id, OrderStatusType status) {
        orderRepository.updateStatus(id, status, OrderStatusType.pending);
    }
}
