package com.online_store.product_service.api.product.dto.response;

import java.util.List;

import com.online_store.product_service.api.product.dto.base.FeatureDto;

public record ProductDetailsDetailResponse(
        Long id,
        String description,
        String shortDescriptioN,
        List<FeatureDto> features,
        List<ProductCriteriaResponse> productCriterias) {

}
