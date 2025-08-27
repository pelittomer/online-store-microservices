package com.online_store.cart_service.client.product;

import java.math.BigDecimal;

public record ProductStockInfo(
        Long id,
        Integer stockQuantity,
        BigDecimal additionalPrice,
        Boolean isLimited,
        Integer replenishQuantity) {

}
