package com.online_store.logistic_service.api.shipper.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shippers")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "logo_id")
    private Long logo;

    @Column(nullable = false)
    private String websiteUrl;

    @Column(nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(nullable = false, unique = true)
    private String apiKey;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Shipper() {
    }

    public Shipper(String name,
            Long logo,
            String websiteUrl,
            String phone,
            String email,
            String address,
            String apiKey) {
        this.name = name;
        this.logo = logo;
        this.websiteUrl = websiteUrl;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.apiKey = apiKey;
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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getApiKey() {
        return apiKey;
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

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
