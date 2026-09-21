package com.ms.inventory_service.repositories;

import com.ms.inventory_service.entities.ProductReservesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReserveRepository extends JpaRepository<ProductReservesEntity,Long> {
    Optional<ProductReservesEntity> findByOrderId(Long id);
}
