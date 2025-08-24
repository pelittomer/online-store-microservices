package com.online_store.upload_service.common.response;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(int status,String message,T data) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), message, data);
    }
}
