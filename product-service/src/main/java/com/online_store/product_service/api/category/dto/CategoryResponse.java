package com.online_store.product_service.api.category.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        Long image,
        Long icon,
        Integer leftValue,
        Integer rightValue,
        Long parent) {

}
