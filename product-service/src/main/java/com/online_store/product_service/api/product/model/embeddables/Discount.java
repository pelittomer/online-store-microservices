package com.online_store.product_service.api.product.model.embeddables;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Discount {
    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    @Column(name = "discount_start_date")
    private LocalDateTime startDate;

    @Column(name = "discount_end_date")
    private LocalDateTime endDate;

    @Column(name = "discount_applied_price", precision = 10, scale = 2)
    private BigDecimal appliedPrice;

    public Discount() {
    }

    public Discount(BigDecimal discountPercentage,
            LocalDateTime startDate,
            LocalDateTime endDate,
            BigDecimal appliedPrice) {
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appliedPrice = appliedPrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAppliedPrice() {
        return appliedPrice;
    }

    public void setAppliedPrice(BigDecimal appliedPrice) {
        this.appliedPrice = appliedPrice;
    }
}
