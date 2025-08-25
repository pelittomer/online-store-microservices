package com.online_store.product_service.api.brand.dto;

import java.time.LocalDateTime;

public record BrandResponse(
        Long id,
        String name,
        Long logo,
        LocalDateTime createdAt) {

}
