package com.online_store.product_service.api.product.dto.response;

import java.util.List;

public record ProductCriteriaResponse(
        Long id,
        ProductVariationResponse variation,
        List<CriteriaOptionResponse> criteriaOptions) {

}
