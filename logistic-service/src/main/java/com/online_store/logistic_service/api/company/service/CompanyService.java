package com.online_store.logistic_service.api.company.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.logistic_service.api.company.dto.request.CompanyRequest;
import com.online_store.logistic_service.api.company.dto.request.CompanyUpdateRequest;
import com.online_store.logistic_service.api.company.dto.request.CompanyUpdateStatusRequest;
import com.online_store.logistic_service.api.company.dto.response.CompanyDetailsResponse;
import com.online_store.logistic_service.api.company.dto.response.CompanyResponse;
import com.online_store.logistic_service.api.company.exception.CompanyAlreadyExistsException;
import com.online_store.logistic_service.api.company.exception.CompanyNotFoundException;
import com.online_store.logistic_service.api.company.exception.CompanyUpdateException;
import com.online_store.logistic_service.api.company.model.Company;
import com.online_store.logistic_service.api.company.model.CompanyStatus;
import com.online_store.logistic_service.api.company.repository.CompanyRepository;
import com.online_store.logistic_service.client.upload.UploadClient;
import com.online_store.logistic_service.client.upload.UploadResponse;
import com.online_store.logistic_service.common.response.ApiResponse;
import com.online_store.logistic_service.common.utils.UtilsService;

@Service
public class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository repository;
    private final UploadClient uploadClient;
    private final UtilsService utilsService;

    public CompanyService(CompanyRepository repository,
            UploadClient uploadClient,
            UtilsService utilsService) {
        this.repository = repository;
        this.uploadClient = uploadClient;
        this.utilsService = utilsService;
    }

    public String createCompany(CompanyRequest dto,
            MultipartFile file) {
        logger.info("Attempting to create a new company with name: {}", dto.name());
        Long userId = utilsService.getCurrentUserId();

        validateCompanyCreation(dto, file, userId);
        ApiResponse<UploadResponse> upload = uploadClient.uploadFile(file);
        Long uploadId = upload.data().upload();

        Company company = createCompanyMapper(dto, uploadId, userId);
        repository.save(company);

        logger.info("Company with ID {} created successfully for user {}", company.getId(), userId);
        return "Company created successfully.";
    }

    public String updateMyCompany(CompanyUpdateRequest dto,
            MultipartFile file) {
        logger.info("Attempting to update company for current user.");

        if (file != null) {
            utilsService.checkImageFileType(file);
        }
        Long userId = utilsService.getCurrentUserId();

        Company company = findByUserCompany(userId);
        updateCompanyMapper(dto, company);
        repository.save(company);

        logger.info("Company with ID {} updated successfully.", company.getId());
        return "Company updated successfully";
    }

    public CompanyDetailsResponse getMyCompany() {
        logger.info("Retrieving company details for current user.");
        Long userId = utilsService.getCurrentUserId();
        Company company = findByUserCompany(userId);
        return companyDetailsMapper(company);
    }

    public String updateCompanyStatus(Long companyId,
            CompanyUpdateStatusRequest dto) {
        logger.info("Attempting to update status for company with ID {}", companyId);
        if (dto.status() == CompanyStatus.REJECTED && dto.rejectionReason() != null) {
            logger.error("Rejection reason cannot be null or empty for a rejected company.");
            throw new CompanyUpdateException("Rejection reason is required when the company status is REJECTED.");
        }

        Company company = findByIdCompany(companyId);
        updateCompanyStatusMapper(dto, company);
        repository.save(company);

        logger.info("Company with ID {} status updated to {}", companyId, dto.status());
        return "Company updated.";
    }

    public CompanyResponse getCompanyById(Long companyId) {
        logger.debug("Fetching company by ID: {}", companyId);
        Company company = findByIdCompany(companyId);
        return companyResponseMapper(company);
    }

    public List<CompanyResponse> listAllCompanies() {
        logger.info("Listing all companies.");
        return repository.findAll().stream()
                .map(this::companyResponseMapper)
                .toList();
    }

    private void validateCompanyCreation(CompanyRequest dto,
            MultipartFile file,
            Long currentUser) {
        logger.debug("Validating company creation request for user {}.", currentUser);
        utilsService.checkImageFileType(file);

        Optional<Company> existingCompany = repository.findByUserIdOrNameOrTaxId(currentUser, dto.name(), dto.taxId());

        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            if (company.getUser().equals(currentUser)) {
                logger.warn("User {} already has a company. Cannot create another one.", currentUser);
                throw new CompanyAlreadyExistsException("You can only create one company per user account.");
            }
            if (company.getName().equalsIgnoreCase(dto.name())) {
                logger.warn("Attempt to create a company with a duplicate name: '{}'.", dto.name());
                throw new CompanyAlreadyExistsException("Company with name '" + dto.name() + "' already exists.");
            }
            if (company.getTaxId().equals(dto.taxId())) {
                logger.warn("Attempt to create a company with a duplicate Tax ID: '{}'.", dto.taxId());
                throw new CompanyAlreadyExistsException("Company with Tax ID '" + dto.taxId() + "' already exists.");
            }
        }
    }

    private Company findByUserCompany(Long userId) {
        logger.debug("Searching for company belonging to user ID: {}", userId);
        return repository.findByUser(userId)
                .orElseThrow(() -> {
                    logger.error("Company not found for user ID: {}", userId);
                    return new CompanyNotFoundException("Company not found for the current user.");
                });
    }

    private Company findByIdCompany(Long companyId) {
        logger.debug("Searching for company by ID: {}", companyId);
        return repository.findById(companyId)
                .orElseThrow(() -> {
                    logger.error("Company not found with ID: {}", companyId);
                    return new CompanyNotFoundException("Company not found with ID: " + companyId);
                });
    }

    private Company createCompanyMapper(CompanyRequest dto, Long uploadId, Long userId) {
        return new Company(
                dto.name(),
                uploadId,
                dto.description(),
                dto.websiteUrl(),
                dto.phone(),
                dto.email(),
                dto.taxId(),
                dto.taxOffice(),
                userId);
    }

    private CompanyDetailsResponse companyDetailsMapper(Company company) {
        return new CompanyDetailsResponse(
                company.getId(),
                company.getName(),
                company.getLogo(),
                company.getDescription(),
                company.getWebsiteUrl(),
                company.getPhone(),
                company.getEmail(),
                company.getTaxId(),
                company.getTaxOffice(),
                company.getStatus(),
                company.getRejectionReason(),
                company.getCreatedAt(),
                company.getUpdatedAt());
    }

    private void updateCompanyStatusMapper(CompanyUpdateStatusRequest dto, Company company) {
        Optional.ofNullable(dto.rejectionReason()).ifPresent(company::setRejectionReason);
        Optional.ofNullable(dto.status()).ifPresent(company::setStatus);
    }

    private void updateCompanyMapper(CompanyUpdateRequest dto,
            Company company) {
        Optional.ofNullable(dto.name()).ifPresent(company::setName);
        Optional.ofNullable(dto.description()).ifPresent(company::setDescription);
        Optional.ofNullable(dto.websiteUrl()).ifPresent(company::setWebsiteUrl);
        Optional.ofNullable(dto.phone()).ifPresent(company::setPhone);
        Optional.ofNullable(dto.email()).ifPresent(company::setEmail);
    }

    private CompanyResponse companyResponseMapper(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getLogo(),
                company.getDescription(),
                company.getWebsiteUrl(),
                company.getPhone(),
                company.getEmail(),
                company.getStatus(),
                company.getCreatedAt(),
                company.getUpdatedAt());
    }
}
