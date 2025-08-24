package com.online_store.logistic_service.api.company.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyRequest(
        @NotBlank(message = "Company name cannot be blank") @Size(max = 255, message = "Company name cannot exceed 255 characters") String name,

        @NotBlank(message = "Description cannot be blank") @Size(max = 1000, message = "Description cannot exceed 1000 characters") String description,

        String websiteUrl,

        String phone,

        @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") @Size(max = 255, message = "Email cannot exceed 255 characters") String email,

        @NotBlank(message = "Tax ID cannot be blank") @Size(min = 10, max = 11, message = "Tax ID must be between 10 and 11 characters (e.g., Turkish tax ID)") @Pattern(regexp = "^[0-9]{10,11}$", message = "Tax ID must contain only digits") String taxId,

        @NotBlank(message = "Tax office cannot be blank") @Size(max = 255, message = "Tax office cannot exceed 255 characters") String taxOffice) {

}
