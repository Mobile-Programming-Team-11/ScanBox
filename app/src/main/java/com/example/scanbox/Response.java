package com.example.scanbox;

public class Response { //VisionAPI의 개별 응답(제품 검색 결과)
    private ProductSearchResults productSearchResults;

    public ProductSearchResults getProductSearchResults() {
        return productSearchResults;
    }

    public void setProductSearchResults(ProductSearchResults productSearchResults) {
        this.productSearchResults = productSearchResults;
    }
}