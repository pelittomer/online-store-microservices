package com.online_store.upload_service.api.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.upload_service.api.upload.model.Upload;

public interface UploadRepository extends JpaRepository<Upload, Long> {

}
