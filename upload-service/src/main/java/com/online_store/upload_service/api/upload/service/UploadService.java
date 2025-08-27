package com.online_store.upload_service.api.upload.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.upload_service.api.upload.dto.UploadResponse;
import com.online_store.upload_service.api.upload.model.Upload;
import com.online_store.upload_service.api.upload.repository.UploadRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private final UploadRepository repository;

    public UploadService(UploadRepository repository) {
        this.repository = repository;
    }

    public UploadResponse createFile(MultipartFile file) throws IOException {
        logger.info("Starting file upload process for file: {}", file.getOriginalFilename());
        try {
            Upload newFile = createFileMapper(file);
            Upload upload = repository.save(newFile);
            logger.info("Successfully uploaded file with ID: {}", upload.getId());
            return new UploadResponse(upload.getId());
        } catch (IOException e) {
            logger.error("Error during file upload for file: {}", file.getOriginalFilename(), e);
            throw e;
        }
    }

    public Upload getUploadById(Long id) {
        logger.debug("Fetching upload record with ID: {}", id);
        return findUploadById(id);
    }

    public String updateFile(Long id, MultipartFile file) throws IOException {
        logger.info("Attempting to update file with ID: {}", id);

        Upload upload = findUploadById(id);
        upload.setFileContent(file.getBytes());
        repository.save(upload);

        logger.info("File with ID: {} updated successfully.", id);
        return "File updated successfully.";
    }

    public String deleteFile(Long id) {
        logger.info("Attempting to delete file with ID: {}", id);

        Upload upload = findUploadById(id);
        repository.delete(upload);

        logger.info("File with ID: {} deleted successfully.", id);
        return "file deleted successfully";
    }

    private Upload createFileMapper(MultipartFile file) throws IOException {
        logger.debug("Mapping MultipartFile to Upload object for file: {}", file.getOriginalFilename());
        return new Upload(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes());
    }

    private Upload findUploadById(Long uploadId) {
        return repository.findById(uploadId)
                .orElseThrow(() -> {
                    logger.warn("Upload with ID {} not found.", uploadId);
                    return new EntityNotFoundException("Upload not found!");
                });
    }

}
