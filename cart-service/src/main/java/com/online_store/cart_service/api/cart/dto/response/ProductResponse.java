package com.online_store.cart_service.api.cart.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
                Long id,
                String name,
                BigDecimal price,
                List<Long> images,
                BrandResponse brand,
                CompanyResponse company,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {

}
