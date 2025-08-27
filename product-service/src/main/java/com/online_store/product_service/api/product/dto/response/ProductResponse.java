package com.online_store.product_service.api.product.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.online_store.product_service.api.brand.dto.BrandResponse;
import com.online_store.product_service.client.logistic.CompanyResponse;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        List<Long> images,
        BrandResponse brand,
        CompanyResponse company,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
