package com.online_store.product_service.api.product.dto.request;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be blank.") String name,

        @NotNull(message = "Price cannot be null.") @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.") BigDecimal price,

        @NotNull(message = "isPublished status cannot be null.") Boolean isPublished,

        @NotNull(message = "Brand ID cannot be null.") @Min(value = 1, message = "Brand ID must be a positive number.") Long brand,

        @NotNull(message = "Shipper ID cannot be null.") @Min(value = 1, message = "Shipper ID must be a positive number.") Long shipper,

        @NotNull(message = "Category ID cannot be null.") @Min(value = 1, message = "Category ID must be a positive number.") Long category,

        @NotNull(message = "Product details cannot be null.") @Valid ProductDetailRequest productDetail,

        @Valid List<ProductStockRequest> productStocks) {

}
