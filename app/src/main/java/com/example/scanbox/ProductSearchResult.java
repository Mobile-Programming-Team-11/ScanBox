package com.example.scanbox;

public class ProductSearchResult {
    private final String imageId;
    private final double score;
    private final String label;
    private final String name;
    private String imageUri;

    public ProductSearchResult(String imageId, double score, String label, String name) {
        this.imageId = imageId;
        this.score = score;
        this.label = label;
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public double getScore() {
        return score;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSearchResult that = (ProductSearchResult) o;

        if (Double.compare(that.score, score) != 0) return false;
        if (!imageId.equals(that.imageId)) return false;
        if (!label.equals(that.label)) return false;
        if (!name.equals(that.name)) return false;
        return imageUri.equals(that.imageUri);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = imageId.hashCode();
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + label.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + imageUri.hashCode();
        return result;
    }
}