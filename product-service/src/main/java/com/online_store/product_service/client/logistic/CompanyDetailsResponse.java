package com.online_store.product_service.client.logistic;

import java.time.LocalDateTime;


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
