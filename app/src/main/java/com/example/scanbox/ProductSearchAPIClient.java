package com.example.scanbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSearchAPIClient {   //VisionAPI와 통신하여 제품 검색을 수행하는 기능. 이미지를 Base64기반으로 변환한 뒤, API 요청 및 응답 처리, 검색 결과를 리턴함.

    private static final int VISION_API_PRODUCT_MAX_RESULT = 5;
    private static final String VISION_API_URL = "https://us-central1-odml-codelabs.cloudfunctions.net/productSearch";
    private static final String VISION_API_KEY = "";
    private static final String VISION_API_PROJECT_ID = "odml-codelabs";
    private static final String VISION_API_LOCATION_ID = "us-east1";
    private static final String VISION_API_PRODUCT_SET_ID = "product_set0";

    private final RequestQueue requestQueue;

    public ProductSearchAPIClient(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    private String convertBitmapToBase64(Bitmap bitmap) {   //Base64를 이용해 이미지를 인코딩
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Task<List<ProductSearchResult>> annotateImage(Bitmap image) throws JSONException {   //VisionAPI에 이미지 주석을 다는 역할.
        TaskCompletionSource<List<ProductSearchResult>> apiSource = new TaskCompletionSource<>();
        Task<List<ProductSearchResult>> apiTask = apiSource.getTask();

        String base64 = convertBitmapToBase64(image);

        String requestJson = "{\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"image\": {\n" +
                "        \"content\": \"" + base64 + "\"\n" +
                "      },\n" +
                "      \"features\": [\n" +
                "        {\n" +
                "          \"type\": \"PRODUCT_SEARCH\",\n" +
                "          \"maxResults\": " + VISION_API_PRODUCT_MAX_RESULT + "\n" +
                "        }\n" +
                "      ],\n" +
                "      \"imageContext\": {\n" +
                "        \"productSearchParams\": {\n" +
                "          \"productSet\": \"projects/" + VISION_API_PROJECT_ID + "/locations/" + VISION_API_LOCATION_ID + "/productSets/" + VISION_API_PRODUCT_SET_ID + "\",\n" +
                "          \"productCategories\": [\n" +
                "            \"apparel-v2\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        requestQueue.add(new JsonObjectRequest( //제품 검색 결과 처리 및 참조된 이미지 리턴.
                JsonObjectRequest.Method.POST,
                VISION_API_URL + "/images:annotate?key=" + VISION_API_KEY,
                new JSONObject(requestJson),
                response -> {
                    try {
                        List<ProductSearchResult> productList = apiResponseToObject(response);
                        List<Task<ProductSearchResult>> fetchReferenceImageTasks = productList.stream()
                                .map(this::fetchReferenceImage)
                                .collect(Collectors.toList());
                        Tasks.whenAllComplete(fetchReferenceImageTasks)
                                .addOnSuccessListener(tasks -> apiSource.setResult(productList))
                                .addOnFailureListener(apiSource::setException);
                    } catch (JsonSyntaxException | JSONException e) {
                        apiSource.setException(e);
                    }
                },
                error -> apiSource.setException(error)
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        });

        return apiTask;
    }

    private Task<ProductSearchResult> fetchReferenceImage(ProductSearchResult searchResult) {
        TaskCompletionSource<ProductSearchResult> apiSource = new TaskCompletionSource<>();
        Task<ProductSearchResult> apiTask = apiSource.getTask();

        String url = VISION_API_URL + "/" + searchResult.getImageId() + "?key=" + VISION_API_KEY;

        StringRequest stringRequest = new StringRequest(
                StringRequest.Method.GET,
                url,
                response -> {
                    try {
                        JSONObject responseJson = new JSONObject(response);
                        String gcsUri = responseJson.getString("uri");
                        String httpUri = gcsUri.replace("gs://", "https://storage.googleapis.com/");
                        searchResult.setImageUri(httpUri);
                        apiSource.setResult(searchResult);
                    } catch (JSONException e) {
                        apiSource.setException(e);
                    }
                },
                apiSource::setException
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        Log.d(ProductSearchFragment.TAG, "Sending API request.");
        requestQueue.add(stringRequest);

        return apiTask;
    }

    private List<ProductSearchResult> apiResponseToObject(JSONObject response) throws JsonSyntaxException, JSONException {
        List<ProductSearchResult> productSearchResults = new java.util.ArrayList<>();
        SearchResultResponse searchResult = new Gson().fromJson(response.toString(), SearchResultResponse.class);
        Log.d(ProductSearchFragment.TAG, "results: " + searchResult);
        List<Response> responses = searchResult.getResponses();
        if (responses != null && !responses.isEmpty()) {
            ProductSearchResults productSearchResultsObj = responses.get(0).getProductSearchResults();
            if (productSearchResultsObj != null) {
                List<Result> results = productSearchResultsObj.getResults();
                if (results != null) {
                    for (Result result : results) {
                        productSearchResults.add(new ProductSearchResult(
                                result.getImage(),
                                result.getScore(),
                                result.getProduct().getProductLabels().stream()
                                        .map(label -> label.getKey() + " - " + label.getValue())
                                        .collect(Collectors.joining(", ")),
                                result.getProduct().getName()
                        ));
                    }
                }
            }
        }
        return productSearchResults;
    }
}