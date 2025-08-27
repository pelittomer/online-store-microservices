package com.online_store.cart_service.common.utils;

import org.springframework.stereotype.Component;

import com.online_store.cart_service.api.cart.dto.response.ProductResponse;
import com.online_store.cart_service.client.product.ProductClient;
import com.online_store.cart_service.client.product.ProductStockInfo;
import com.online_store.cart_service.client.user.UserClient;

@Component
public class UtilsService {
    private final UserClient userClient;
    private final ProductClient productClient;

    public UtilsService(UserClient userClient,
            ProductClient productClient) {
        this.userClient = userClient;
        this.productClient = productClient;
    }

    public Long getCurrentUserId() {
        return userClient.getCurrentUser().data().id();
    }

    public ProductStockInfo getProductStock(Long productId,
            Long stockId) {
        return productClient.getProductStock(productId, stockId).data();
    }

    public ProductResponse getProductResponse(Long id) {
        return productClient.getProduct(id).data();
    }
}
