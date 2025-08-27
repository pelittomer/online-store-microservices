package com.online_store.product_service.api.category.dto;

import java.util.List;

public class CategoryTreeResponse {
    private Long id;
    private String name;
    private String description;
    private Long image;
    private Long icon;
    private List<CategoryTreeResponse> children;

    public CategoryTreeResponse() {
    }

    public CategoryTreeResponse(Long id, String name, String description, Long image, Long icon,
            List<CategoryTreeResponse> children) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.icon = icon;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public Long getIcon() {
        return icon;
    }

    public void setIcon(Long icon) {
        this.icon = icon;
    }

    public List<CategoryTreeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryTreeResponse> children) {
        this.children = children;
    }
}
