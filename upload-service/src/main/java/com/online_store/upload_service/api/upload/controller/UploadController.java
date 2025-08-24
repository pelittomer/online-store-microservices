package com.online_store.upload_service.api.upload.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.upload_service.api.upload.dto.UploadResponse;
import com.online_store.upload_service.api.upload.model.Upload;
import com.online_store.upload_service.api.upload.service.UploadService;
import com.online_store.upload_service.common.response.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final UploadService service;

    public UploadController(UploadService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UploadResponse>> createFile(
            @RequestPart(name = "file", required = true) MultipartFile file) throws IOException {
        return ResponseEntity.ok(
                ApiResponse.success("",
                        service.createFile(file)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getUploadById(@PathVariable Long id) {
        Upload uploadFile = service.getUploadById(id);
        Resource resource = new ByteArrayResource(uploadFile.getFileContent());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + uploadFile.getFileName() + "\"");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadFile.getFileType()))
                .headers(headers)
                .body(resource);
    }

}
