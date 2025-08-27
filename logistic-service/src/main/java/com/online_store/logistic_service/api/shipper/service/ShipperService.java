package com.online_store.logistic_service.api.shipper.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.logistic_service.api.shipper.dto.ShipperRequest;
import com.online_store.logistic_service.api.shipper.dto.ShipperResponse;
import com.online_store.logistic_service.api.shipper.exception.ShipperAlreadyExistsException;
import com.online_store.logistic_service.api.shipper.exception.ShipperNotFoundException;
import com.online_store.logistic_service.api.shipper.model.Shipper;
import com.online_store.logistic_service.api.shipper.repository.ShipperRepository;
import com.online_store.logistic_service.client.upload.UploadClient;
import com.online_store.logistic_service.client.upload.UploadResponse;
import com.online_store.logistic_service.common.response.ApiResponse;
import com.online_store.logistic_service.common.utils.UtilsService;

@Service
public class ShipperService {
    private static final Logger logger = LoggerFactory.getLogger(ShipperService.class);

    private final ShipperRepository repository;
    private final UtilsService utilsService;
    private final UploadClient uploadClient;

    public ShipperService(ShipperRepository repository,
            UtilsService utilsService,
            UploadClient uploadClient) {
        this.repository = repository;
        this.utilsService = utilsService;
        this.uploadClient = uploadClient;
    }

    public String createShipper(ShipperRequest dto,
            MultipartFile file) {
        logger.info("Attempting to create new shipper: {}", dto.name());
        utilsService.checkImageFileType(file);

        shipperValidation(dto);

        ApiResponse<UploadResponse> upload = uploadClient.uploadFile(file);
        Long uploadId = upload.data().upload();

        Shipper shipper = createShipperMapper(dto, uploadId);
        repository.save(shipper);

        logger.info("Shipper with ID {} created successfully.", shipper.getId());
        return "Shipper created successfully.";
    }

    public List<ShipperResponse> listShippers() {
        logger.info("Fetching all shippers.");
        return repository.findAll().stream()
                .map(this::shipperResponseMapper).toList();
    }

    public ShipperResponse getShipperById(Long shipperId) {
        logger.info("Fetching shipper by ID: {}", shipperId);
        Shipper shipper = findByShipperId(shipperId);
        return shipperResponseMapper(shipper);
    }

    private Shipper findByShipperId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Shipper not found with ID: {}", id);
                    return new ShipperNotFoundException("Shipper not found with ID: " + id);
                });
    }

    private void shipperValidation(ShipperRequest dto) {
        logger.debug("Validating shipper creation request for name: {} and email: {}", dto.name(), dto.email());

        if (repository.existsByName(dto.name())) {
            logger.warn("Shipper creation failed: Name '{}' already exists.", dto.name());
            throw new ShipperAlreadyExistsException("Shipper with name '" + dto.name() + "' already exists.");
        }

        if (repository.existsByEmail(dto.email())) {
            logger.warn("Shipper creation failed: Email '{}' already exists.", dto.email());
            throw new ShipperAlreadyExistsException("Shipper with email '" + dto.email() + "' already exists.");
        }

    }

    private String generateUniqueApiKey() {
        logger.debug("Generating unique API key.");
        String apiKey;
        do {
            apiKey = UUID.randomUUID().toString().replace("-", "");
        } while (repository.findByApiKey(apiKey).isPresent());
        logger.debug("Generated unique API key.");
        return apiKey;
    }

    private Shipper createShipperMapper(ShipperRequest dto, Long uploadId) {
        String apiKey = generateUniqueApiKey();
        return new Shipper(
                dto.name(),
                uploadId,
                dto.websiteUrl(),
                dto.phone(),
                dto.email(),
                dto.address(),
                apiKey);
    }

    private ShipperResponse shipperResponseMapper(Shipper shipper) {
        return new ShipperResponse(
                shipper.getId(),
                shipper.getName(),
                shipper.getLogo(),
                shipper.getWebsiteUrl(),
                shipper.getPhone(),
                shipper.getEmail(),
                shipper.getAddress(),
                shipper.getIsActive(),
                shipper.getCreatedAt());
    }

}
