package com.online_store.product_service.api.product.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.online_store.product_service.api.product.model.embeddables.StockVariation;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_stocks")
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "additional_price")
    private BigDecimal additionalPrice;

    @Column(name = "is_limited")
    private Boolean isLimited;

    @Column(name = "replenish_quantity")
    private Integer replenishQuantity;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_stock_variation", joinColumns = @JoinColumn(name = "product_stock_id"))
    private Set<StockVariation> stockVariations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductStock() {
    }

    public ProductStock(Integer stockQuantity,
            BigDecimal additionalPrice,
            Boolean isLimited,
            Integer replenishQuantity,
            Set<StockVariation> stockVariations) {
        this.stockQuantity = stockQuantity;
        this.additionalPrice = additionalPrice;
        this.isLimited = isLimited;
        this.replenishQuantity = replenishQuantity;
        this.stockVariations = stockVariations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public Boolean getIsLimited() {
        return isLimited;
    }

    public void setIsLimited(Boolean isLimited) {
        this.isLimited = isLimited;
    }

    public Integer getReplenishQuantity() {
        return replenishQuantity;
    }

    public void setReplenishQuantity(Integer replenishQuantity) {
        this.replenishQuantity = replenishQuantity;
    }

    public Set<StockVariation> getStockVariations() {
        return stockVariations;
    }

    public void setStockVariations(Set<StockVariation> stockVariations) {
        this.stockVariations = stockVariations;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
