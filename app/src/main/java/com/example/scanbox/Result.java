package com.example.scanbox;

public class Result {   //개별 제품 검색 결과
    private Product product;    //제품 정보
    private double score;   //유사도
    private String image;   //이미지 URL

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}