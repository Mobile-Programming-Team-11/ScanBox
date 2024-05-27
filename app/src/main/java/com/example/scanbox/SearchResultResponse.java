package com.example.scanbox;

import java.util.List;

public class SearchResultResponse { //VisionAPI의 전체 응답 클래스. Response 객체를 포함
    private List<Response> responses;

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}