package com.online_store.logistic_service.api.company.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.logistic_service.api.company.dto.request.CompanyRequest;
import com.online_store.logistic_service.api.company.dto.request.CompanyUpdateRequest;
import com.online_store.logistic_service.api.company.dto.request.CompanyUpdateStatusRequest;
import com.online_store.logistic_service.api.company.dto.response.CompanyDetailsResponse;
import com.online_store.logistic_service.api.company.dto.response.CompanyResponse;
import com.online_store.logistic_service.api.company.service.CompanyService;
import com.online_store.logistic_service.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
        private final CompanyService service;

        public CompanyController(CompanyService service) {
                this.service = service;
        }

        @PostMapping
        public ResponseEntity<ApiResponse<String>> createCompany(
                        @Valid @RequestPart("company") CompanyRequest companyRequest,
                        @RequestPart("file") MultipartFile file) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.createCompany(companyRequest, file)));
        }

        @PutMapping
        public ResponseEntity<ApiResponse<String>> updateMyCompany(
                        @Valid @RequestPart("company") CompanyUpdateRequest companyUpdateRequest,
                        @RequestPart(value = "file", required = false) MultipartFile file) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.updateMyCompany(companyUpdateRequest, file)));
        }

        @GetMapping
        public ResponseEntity<ApiResponse<CompanyDetailsResponse>> getMyCompany() {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.getMyCompany()));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<String>> updateCompanyStatus(@PathVariable Long id,
                        @RequestBody CompanyUpdateStatusRequest companyUpdateStatusRequest) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.updateCompanyStatus(id, companyUpdateStatusRequest)));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<CompanyResponse>> getCompanyById(@PathVariable Long id) {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.getCompanyById(id)));
        }

        @GetMapping("/all")
        public ResponseEntity<ApiResponse<List<CompanyResponse>>> listAllCompanies() {
                return ResponseEntity.ok(
                                ApiResponse.success("",
                                                service.listAllCompanies()));
        }
}
