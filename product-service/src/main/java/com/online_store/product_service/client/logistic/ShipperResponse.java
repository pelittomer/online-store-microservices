package com.online_store.product_service.client.logistic;

import java.time.LocalDateTime;

public record ShipperResponse(
        Long id,
        String name,
        Long logo,
        String websiteUrl,
        String phone,
        String email,
        String address,
        Boolean isActive,
        LocalDateTime createdAt) {

}
