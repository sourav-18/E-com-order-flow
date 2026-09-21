package com.ms.inventory_service.services;

import com.ms.inventory_service.clients.OrderClient;
import com.ms.inventory_service.dtos.OrderDto;
import com.ms.inventory_service.dtos.ProductReserveCreateResponseDto;
import com.ms.inventory_service.entities.ProductReservesEntity;
import com.ms.inventory_service.exceptions.DuplicateProductReserveException;
import com.ms.inventory_service.mapper.ProductReserveMapper;
import com.ms.inventory_service.repositories.ProductRepository;
import com.ms.inventory_service.repositories.ProductReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReserveService {

    private final ProductReserveRepository productReserveRepository;
    private final OrderClient orderClient;
    private String nextApiUrl="http://localhost:9003/api/v1/payments";  //todo move to utils

    @Transactional
    public ProductReserveCreateResponseDto reserved(Long orderId){
        OrderDto orderDetails = orderClient.details(orderId);
        ProductReservesEntity productReservesEntity = productReserveRepository.findByOrderId(orderId).orElse(null);
        if(productReservesEntity!=null){
            throw new DuplicateProductReserveException("product already reserved for this order");
        }
        ProductReservesEntity newReserved = ProductReserveMapper.toEntity(orderId, orderDetails.getProductId(),orderDetails.getQuantity());
        productReserveRepository.save(newReserved);
        return ProductReserveMapper.toDto(newReserved, orderDetails.getFinalTotalPrice(), nextApiUrl);
    }
}
