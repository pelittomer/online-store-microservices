package com.online_store.cart_service.api.cart.dto.response;

import java.time.LocalDateTime;


public record CompanyResponse(
        Long id,
        String name,
        Long logo,
        String description,
        String websiteUrl,
        String phone,
        String email,
        CompanyStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
