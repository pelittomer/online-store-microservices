package com.online_store.product_service.client.logistic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.online_store.product_service.common.response.ApiResponse;

@FeignClient(name = "LOGISTICS-SERVICE")
public interface LogisticClient {
    @GetMapping("/api/company")
    ApiResponse<CompanyDetailsResponse> getMyCompany();

    @GetMapping("/api/company/{id}")
    ApiResponse<CompanyResponse> getCompanyById(@PathVariable Long id);

    @GetMapping("/api/shipper/{id}")
    ApiResponse<ShipperResponse> getShipperById(@PathVariable Long id);
}
