package com.online_store.logistic_service.api.company.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JoinColumn(name = "logo_id", referencedColumnName = "id", unique = true)
    private Long logo;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private String websiteUrl;

    @Column(nullable = true)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "tax_id", unique = true, nullable = false)
    private String taxId;

    @Column(name = "tax_office", nullable = false)
    private String taxOffice;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status = CompanyStatus.PENDING;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @JoinColumn(name = "user_id", nullable = false)
    private Long user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Company() {
    }

    public Company(String name,
            Long logo,
            String description,
            String websiteUrl,
            String phone,
            String email,
            String taxId,
            String taxOffice,
            Long user) {
        this.name = name;
        this.logo = logo;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.phone = phone;
        this.email = email;
        this.taxId = taxId;
        this.taxOffice = taxOffice;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getTaxId() {
        return taxId;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Long getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public void setStatus(CompanyStatus status) {
        this.status = status;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}