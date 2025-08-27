package com.online_store.cart_service.api.cart.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountDto(
        BigDecimal discountPercentage,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal appliedPrice) {

}
