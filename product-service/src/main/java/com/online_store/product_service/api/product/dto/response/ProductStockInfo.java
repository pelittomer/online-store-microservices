package com.online_store.product_service.api.product.dto.response;

import java.math.BigDecimal;

public record ProductStockInfo(
        Long id,
        Integer stockQuantity,
        BigDecimal additionalPrice,
        Boolean isLimited,
        Integer replenishQuantity) {

}
