package com.online_store.product_service.api.product.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.online_store.product_service.api.brand.model.Brand;
import com.online_store.product_service.api.category.model.Category;
import com.online_store.product_service.api.product.model.embeddables.Discount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Embedded
    private Discount discount;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @JoinTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "upload_id"))
    private Set<Long> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @JoinColumn(name = "shipper_id", nullable = false)
    private Long shipper;

    @JoinColumn(name = "company_id", nullable = false)
    private Long company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private ProductDetail productDetail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductStock> productStocks = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Product() {
    }

    public Product(
            String name,
            BigDecimal price,
            Boolean isPublished,
            Set<Long> images,
            Brand brand,
            Long shipper,
            Long company,
            Category category,
            ProductDetail productDetail,
            List<ProductStock> productStocks) {
        this.name = name;
        this.price = price;
        this.isPublished = isPublished;
        this.images = images;
        this.brand = brand;
        this.shipper = shipper;
        this.company = company;
        this.category = category;
        this.productDetail = productDetail;
        this.productStocks = productStocks;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public Set<Long> getImages() {
        return images;
    }

    public Brand getBrand() {
        return brand;
    }

    public Long getShipper() {
        return shipper;
    }

    public Long getCompany() {
        return company;
    }

    public Category getCategory() {
        return category;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public List<ProductStock> getProductStocks() {
        return productStocks;
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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public void setImages(Set<Long> images) {
        this.images = images;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setShipper(Long shipper) {
        this.shipper = shipper;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public void setProductStocks(List<ProductStock> productStocks) {
        this.productStocks = productStocks;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
