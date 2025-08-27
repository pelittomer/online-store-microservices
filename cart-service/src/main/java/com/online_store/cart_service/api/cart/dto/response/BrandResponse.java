package com.online_store.cart_service.api.cart.dto.response;

import java.time.LocalDateTime;

public record BrandResponse(
        Long id,
        String name,
        Long logo,
        LocalDateTime createdAt) {

}
