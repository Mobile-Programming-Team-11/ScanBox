package com.example.scanbox;

import java.util.List;

public class ProductSearchResults { //제품 검색 결과의 상위 객체. 여러 개의 개별 결과가 출력됨
    private String indexTime;   //인덱스 생성 시간
    private List<Result> results;   //개별 제품 검색 결과 리스트
    private List<ProductGroupedResult> productGroupedResults;   //그룹화된 제품 검색 결과 리스트

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