package com.online_store.logistic_service.api.company.dto.response;

import java.time.LocalDateTime;

import com.online_store.logistic_service.api.company.model.CompanyStatus;

public record CompanyDetailsResponse(
        Long id,
        String name,
        Long logo,
        String description,
        String websiteUrl,
        String phone,
        String email,
        String taxId,
        String taxtOffice,
        CompanyStatus status,
        String rejectionReason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
