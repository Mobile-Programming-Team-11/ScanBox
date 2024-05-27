package com.example.scanbox;

import java.util.List;

public class ProductSearchResults {
    private String indexTime;
    private List<Result> results;
    private List<ProductGroupedResult> productGroupedResults;

    public String getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(String indexTime) {
        this.indexTime = indexTime;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<ProductGroupedResult> getProductGroupedResults() {
        return productGroupedResults;
    }

    public void setProductGroupedResults(List<ProductGroupedResult> productGroupedResults) {
        this.productGroupedResults = productGroupedResults;
    }
}