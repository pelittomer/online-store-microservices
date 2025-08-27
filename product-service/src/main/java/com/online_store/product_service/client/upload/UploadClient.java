package com.online_store.product_service.client.upload;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.common.response.ApiResponse;

import feign.Headers;

@FeignClient(name = "UPLOAD-SERVICE")
public interface UploadClient {
    @PostMapping(value = "/api/upload", consumes = "multipart/form-data")
    @Headers("Content-Type: multipart/form-data")
    ApiResponse<UploadResponse> uploadFile(@RequestPart("file") MultipartFile file);
}
