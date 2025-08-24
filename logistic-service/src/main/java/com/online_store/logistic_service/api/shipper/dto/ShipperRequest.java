package com.online_store.logistic_service.api.shipper.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShipperRequest(
        @NotBlank(message = "Shipper name cannot be blank") @Size(max = 100, message = "Shipper name cannot exceed 100 characters") String name,

        @NotBlank(message = "Website URL cannot be blank") String websiteUrl,

        @NotBlank(message = "Phone number cannot be blank") String phone,

        @NotBlank(message = "Email cannot be blank") @Email(message = "Invalid email format") @Size(max = 255, message = "Email cannot exceed 255 characters") String email,

        @NotBlank(message = "Address cannot be blank") @Size(max = 500, message = "Address cannot exceed 500 characters") String address) {

}
