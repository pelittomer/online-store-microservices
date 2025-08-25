package com.online_store.product_service.api.product.model;

import java.util.HashSet;
import java.util.Set;

import com.online_store.product_service.api.variation.model.VariationOption;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "criteria_options")
public class CriteriaOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_option_id", nullable = false)
    private VariationOption variationOption;

    @JoinTable(name = "criteria_option_images", joinColumns = @JoinColumn(name = "criteria_option_id"), inverseJoinColumns = @JoinColumn(name = "upload_id"))
    private Set<Long> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_criteria_id", nullable = false)
    private ProductCriteria productCriteria;

    public CriteriaOption() {
    }

    public CriteriaOption(VariationOption variationOption,
            ProductCriteria productCriteria,
            Set<Long> images) {
        this.variationOption = variationOption;
        this.productCriteria = productCriteria;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VariationOption getVariationOption() {
        return variationOption;
    }

    public void setVariationOption(VariationOption variationOption) {
        this.variationOption = variationOption;
    }

    public Set<Long> getImages() {
        return images;
    }

    public void setImages(Set<Long> images) {
        this.images = images;
    }

    public ProductCriteria getProductCriteria() {
        return productCriteria;
    }

    public void setProductCriteria(ProductCriteria productCriteria) {
        this.productCriteria = productCriteria;
    }
}
