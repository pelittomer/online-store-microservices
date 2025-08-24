package com.online_store.logistic_service.api.shipper.dto;

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
