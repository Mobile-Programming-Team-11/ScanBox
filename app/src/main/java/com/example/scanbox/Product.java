package com.example.scanbox;

import java.util.List;

public class Product {
    private String name;
    private String displayName;
    private String productCategory;
    private List<ProductLabel> productLabels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductLabel> getProductLabels() {
        return productLabels;
    }

    public void setProductLabels(List<ProductLabel> productLabels) {
        this.productLabels = productLabels;
    }
}