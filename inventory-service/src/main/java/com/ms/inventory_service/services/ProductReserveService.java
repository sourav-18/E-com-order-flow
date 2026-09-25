package com.ms.inventory_service.services;

import com.ms.inventory_service.clients.OrderClient;
import com.ms.inventory_service.dtos.*;
import com.ms.inventory_service.entities.ProductEntity;
import com.ms.inventory_service.entities.ProductReservesEntity;
import com.ms.inventory_service.entities.types.ProductReservesStatusType;
import com.ms.inventory_service.exceptions.DataNotFoundException;
import com.ms.inventory_service.exceptions.DuplicateProductReserveException;
import com.ms.inventory_service.mapper.ProductReserveMapper;
import com.ms.inventory_service.repositories.ProductRepository;
import com.ms.inventory_service.repositories.ProductReserveRepository;
import com.ms.inventory_service.utils.StatusUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductReserveService {

    private final ProductReserveRepository productReserveRepository;
    private final OrderClient orderClient;
    private final ProductService productService;
    private String nextApiUrl = "http://localhost:9003/api/v1/payments";  //todo move to utils

    @Transactional
    public ProductReserveCreateResponseDto reserved(Long orderId) {
        OrderDto orderDetails = orderClient.details(orderId);
        ProductReservesEntity productReservesEntity = productReserveRepository.findByOrderId(orderId).orElse(null);
        if (productReservesEntity != null) {
            throw new DuplicateProductReserveException("product already reserved for this order");
        }
        ProductReservesEntity newReserved = ProductReserveMapper.toEntity(orderId,
                orderDetails.getProductId(),
                orderDetails.getQuantity(),
                orderDetails.getFinalTotalPrice()
        );
        productReserveRepository.save(newReserved);
        return ProductReserveMapper.toDto(newReserved, orderDetails.getFinalTotalPrice(), nextApiUrl);
    }

    public ProductReserveDto details(Long orderId) {
        ProductReservesEntity productReservesEntity = productReserveRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DataNotFoundException("order is not reserved yet"));
        return ProductReserveMapper.toDto(productReservesEntity);
    }

    public void statusUpdate(PaymentStatusDto paymentStatusDto) {
        ProductReservesStatusType status = StatusUtils
                .PaymentStatusToProductReservesStatus(paymentStatusDto.getStatus());
        Integer updated = productReserveRepository.updateStatus(paymentStatusDto.getPaymentId(), status, ProductReservesStatusType.pending);
        if (updated == 0) return;
        if (status == ProductReservesStatusType.cancel) {
            ProductReservesEntity productReservesEntity = productReserveRepository.findByOrderId(paymentStatusDto.getOrderId()).orElse(null);
            if (productReservesEntity == null) return;
            productService.restoreAvailableQuantity(productReservesEntity.getProductId(), productReservesEntity.getQuantity());
        }
    }
}
