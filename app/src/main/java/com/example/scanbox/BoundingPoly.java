package com.example.scanbox;

import java.util.List;

public class BoundingPoly { //바운딩 폴리곤의 좌표
    //바운딩 폴리곤: 객체의 위치와 크기를 나타내는 방법. 이미지를 분석할 때 쓰임.
    // 꼭지점(vertex):: 바운딩 폴리곤을 구성하는 점으로, (x, y)로 표현됨
    // 폴리곤: 여러 vertex들을 연결하여 형성된 도형으로, 이 폴리곤을 통해 사물을 인식하고 검색을 수행하게 된다.
    private List<NormalizedVertex> normalizedVertices;

    public List<NormalizedVertex> getNormalizedVertices() {
        return normalizedVertices;
    }

    public void setNormalizedVertices(List<NormalizedVertex> normalizedVertices) {
        this.normalizedVertices = normalizedVertices;
    }
}