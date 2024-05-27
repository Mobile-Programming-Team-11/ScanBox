package com.example.scanbox;

import java.util.List;

public class SearchResultResponse {
    private List<Response> responses;

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}