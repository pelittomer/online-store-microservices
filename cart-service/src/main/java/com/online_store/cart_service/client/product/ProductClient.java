package com.online_store.cart_service.client.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.online_store.cart_service.api.cart.dto.response.ProductResponse;
import com.online_store.cart_service.config.response.ApiResponse;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @GetMapping("/api/product/{productId}/stock/{stockId}")
    ApiResponse<ProductStockInfo> getProductStock(
            @PathVariable("productId") Long productId,
            @PathVariable("stockId") Long stockId);

    @GetMapping("/api/product/{id}")
    ApiResponse<ProductResponse> getProduct(
            @PathVariable("id") Long id);

}
