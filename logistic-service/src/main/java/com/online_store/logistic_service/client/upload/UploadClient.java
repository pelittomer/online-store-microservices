package com.online_store.logistic_service.client.upload;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.online_store.logistic_service.common.response.ApiResponse;

@FeignClient(name = "UPLOAD-SERVICE")
public interface UploadClient {
    @PostMapping("/api/upload")
    ApiResponse<UploadResponse> uploadFile();
}
