package com.online_store.product_service.api.product.model;

import java.util.HashSet;
import java.util.Set;

import com.online_store.product_service.api.variation.model.Variation;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_criteria")
public class ProductCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;

    @OneToMany(mappedBy = "productCriteria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CriteriaOption> criteriaOptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    public ProductCriteria() {
    }

    public ProductCriteria(Variation variation,
            Set<CriteriaOption> criteriaOptions) {
        this.variation = variation;
        this.criteriaOptions = criteriaOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Variation getVariation() {
        return variation;
    }

    public void setVariation(Variation variation) {
        this.variation = variation;
    }

    public Set<CriteriaOption> getCriteriaOptions() {
        return criteriaOptions;
    }

    public void setCriteriaOptions(Set<CriteriaOption> criteriaOptions) {
        this.criteriaOptions = criteriaOptions;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
