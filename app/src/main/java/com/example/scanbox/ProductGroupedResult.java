package com.example.scanbox;

import java.util.List;

public class ProductGroupedResult { //특정 바운딩 폴리곤 내의 그룹화된 제품 검색 결과 표시.
    private BoundingPoly boundingPoly;
    private List<Result> results;   //바운딩 폴리곤 내의 개별 결과 리스트

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}