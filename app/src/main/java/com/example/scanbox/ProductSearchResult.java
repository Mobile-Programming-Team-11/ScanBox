//package com.example.scanbox;
//
//import java.util.Objects;
//
//public class ProductSearchResult {
//    private String title;
//    private String link;
//    private String imageUrl;
//
//    public ProductSearchResult(String title, String link, String imageUrl) {
//        this.title = title;
//        this.link = link;
//        this.imageUrl = imageUrl;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getLink() {
//        return link;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductSearchResult that = (ProductSearchResult) o;
//        return Objects.equals(link, that.link) &&
//                Objects.equals(imageUrl, that.imageUrl);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(link, imageUrl);
//    }
//}



package com.example.scanbox;

import java.util.Objects;

public class ProductSearchResult {
    private String title;
    private String imageUrl;
    private String link;

    public ProductSearchResult(String title, String imageUrl, String link) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSearchResult that = (ProductSearchResult) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, imageUrl, link);
    }
}