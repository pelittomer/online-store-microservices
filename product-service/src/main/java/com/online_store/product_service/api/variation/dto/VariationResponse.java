package com.online_store.product_service.api.variation.dto;

import java.util.List;

public record VariationResponse(
        Long id,
        String name,
        List<VariationOptionResponse> options) {

}
