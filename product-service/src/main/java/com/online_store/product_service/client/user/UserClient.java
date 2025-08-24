package com.online_store.product_service.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.online_store.product_service.common.response.ApiResponse;

@FeignClient(name = "AUTH-SERVICE")
public interface UserClient {

    @GetMapping("/api/user")
    ApiResponse<UserResponse> getCurrentUser();
}
