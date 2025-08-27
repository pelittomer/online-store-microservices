package com.online_store.product_service.api.product.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductStockResponse(
        Long id,
        Integer stockQuantity,
        BigDecimal additionalPrice,
        Boolean isLimited,
        Integer replenishQuantity,
        List<StockVariationResponse> stockVariations) {

}
