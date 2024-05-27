package com.example.scanbox;

import java.util.List;

public class BoundingPoly {
    private List<NormalizedVertex> normalizedVertices;

    public List<NormalizedVertex> getNormalizedVertices() {
        return normalizedVertices;
    }

    public void setNormalizedVertices(List<NormalizedVertex> normalizedVertices) {
        this.normalizedVertices = normalizedVertices;
    }
}