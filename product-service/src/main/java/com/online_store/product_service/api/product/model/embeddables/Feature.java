package com.online_store.product_service.api.product.model.embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public class Feature {
    private String name;
    private String value;

    public Feature() {
    }

    public Feature(String name,
            String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
