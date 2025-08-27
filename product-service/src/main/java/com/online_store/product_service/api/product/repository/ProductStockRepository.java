package com.online_store.product_service.api.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.online_store.product_service.api.product.model.ProductStock;


public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    @Query("SELECT s FROM ProductStock s WHERE s.product.id = :productId AND s.id = :stockId")
    Optional<ProductStock> findProductStockByProductIdAndStockId(@Param("productId") Long productId,
            @Param("stockId") Long stockId);
}
