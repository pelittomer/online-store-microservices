package com.online_store.product_service.api.product.dto.base;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountDto(
        BigDecimal discountPercentage,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal appliedPrice) {

}
