package com.online_store.product_service.api.product.dto.response;

import com.online_store.product_service.api.variation.dto.VariationOptionResponse;

public record StockVariationResponse(
        Long id,
        String variation,
        VariationOptionResponse variationOption) {

}
