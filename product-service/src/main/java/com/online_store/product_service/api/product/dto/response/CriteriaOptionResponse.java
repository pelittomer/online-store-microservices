package com.online_store.product_service.api.product.dto.response;

import java.util.List;

public record CriteriaOptionResponse(
        Long id,
        ProductVariationOptionResponse variationOption,
        List<Long> images) {

}
