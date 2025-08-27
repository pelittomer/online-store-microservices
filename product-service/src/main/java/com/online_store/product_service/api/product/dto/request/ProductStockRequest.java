package com.online_store.product_service.api.product.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductStockRequest(
                @NotNull(message = "Stock quantity cannot be null.") @Min(value = 0, message = "Stock quantity cannot be negative.") Integer stockQuantity,

                @DecimalMin(value = "0.0", message = "Additional price cannot be negative.") BigDecimal additionalPrice,

                @NotNull(message = "isLimited status cannot be null.") Boolean isLimited,

                @Min(value = 0, message = "Replenish quantity cannot be negative.") Integer replenishQuantity,

                @Valid List<StockVariationRequest> stockVariations) {

}
