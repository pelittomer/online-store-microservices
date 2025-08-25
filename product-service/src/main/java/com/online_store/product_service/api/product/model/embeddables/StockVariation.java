package com.online_store.product_service.api.product.model.embeddables;

import com.online_store.product_service.api.variation.model.Variation;
import com.online_store.product_service.api.variation.model.VariationOption;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class StockVariation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_id", nullable = false)
    private Variation variation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_option_id", nullable = false)
    private VariationOption variationOption;

    public StockVariation() {
    }

    public StockVariation(Variation variation, VariationOption variationOption) {
        this.variation = variation;
        this.variationOption = variationOption;
    }

    public Variation getVariation() {
        return variation;
    }

    public void setVariation(Variation variation) {
        this.variation = variation;
    }

    public VariationOption getVariationOption() {
        return variationOption;
    }

    public void setVariationOption(VariationOption variationOption) {
        this.variationOption = variationOption;
    }
}
