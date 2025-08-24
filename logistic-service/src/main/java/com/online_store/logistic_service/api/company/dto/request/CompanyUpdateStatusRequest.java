package com.online_store.logistic_service.api.company.dto.request;

import com.online_store.logistic_service.api.company.model.CompanyStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompanyUpdateStatusRequest(
        @NotNull(message = "Company status cannot be null") CompanyStatus status,

        @Size(max = 500, message = "Rejection reason cannot exceed 500 characters") String rejectionReason) {

}
