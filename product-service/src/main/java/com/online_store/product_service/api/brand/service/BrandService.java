package com.online_store.product_service.api.brand.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.api.brand.dto.BrandRequest;
import com.online_store.product_service.api.brand.dto.BrandResponse;
import com.online_store.product_service.api.brand.exception.BrandAlreadyExistsException;
import com.online_store.product_service.api.brand.model.Brand;
import com.online_store.product_service.api.brand.repository.BrandRepository;
import com.online_store.product_service.client.upload.UploadClient;
import com.online_store.product_service.client.upload.UploadResponse;
import com.online_store.product_service.common.response.ApiResponse;
import com.online_store.product_service.common.utils.UtilsService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BrandService {
    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

    private final BrandRepository repository;
    private final UtilsService utilsService;
    private final UploadClient uploadClient;

    public BrandService(BrandRepository repository,
            UtilsService utilsService,
            UploadClient uploadClient) {
        this.repository = repository;
        this.utilsService = utilsService;
        this.uploadClient = uploadClient;
    }

    public String createBrand(BrandRequest dto,
            MultipartFile file) {
        logger.info("Attempting to create a new brand with name: {}", dto.name());

        utilsService.checkImageFileType(file);
        validateBrandNameUniqueness(dto.name());

        ApiResponse<UploadResponse> upload = uploadClient.uploadFile(file);
        Long uploadId = upload.data().upload();

        Brand brand = createBrandMapper(dto, uploadId);
        repository.save(brand);

        logger.info("Brand with ID {} created successfully.", brand.getId());
        return "Brand created successfully.";
    }

    public List<BrandResponse> listBrands() {
        logger.info("Listing all brands.");
        return repository.findAll().stream()
                .map(this::brandResponseMapper).toList();
    }

    private void validateBrandNameUniqueness(String name) {
        if (repository.existsByName(name)) {
            logger.warn("Brand creation failed: a brand with name '{}' already exists.", name);
            throw new BrandAlreadyExistsException("Brand with name '" + name + "' already exists.");
        }
    }

    public Brand findByIdBrand(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found."));
    }

    private Brand createBrandMapper(BrandRequest dto, Long uploadId) {
        return new Brand(
                dto.name(),
                uploadId);
    }

    public BrandResponse brandResponseMapper(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getLogo(),
                brand.getCreatedAt());
    }
}
