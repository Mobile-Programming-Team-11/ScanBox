//package com.example.scanbox;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.scanbox.databinding.FragmentProductSearchBinding;
//
//import org.json.JSONException;
//
//import java.io.InputStream;
//import java.util.List;
//
//public class ProductSearchFragment extends Fragment {   //VisionAPI를 이용한 제품 검색 수행 기능.
//
//    public static final String TAG = "ProductSearchFragment";
//    private FragmentProductSearchBinding binding;
//    private ProductSearchAPIClient apiClient;
//    private Bitmap queryImageBitmap;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentProductSearchBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        apiClient = new ProductSearchAPIClient(requireContext());
//
//        if (getArguments() != null) {
//            Uri queryImageUri = getArguments().getParcelable("queryImage");
//            if (queryImageUri != null) {
//                try {
//                    InputStream inputStream = requireContext().getContentResolver().openInputStream(queryImageUri);
//                    queryImageBitmap = BitmapFactory.decodeStream(inputStream);
//                    binding.ivQueryImage.setImageBitmap(queryImageBitmap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        initViews();
//    }
//
//    private void initViews() {  //검색 결과를 fragment 하단 recyclerView에 표시하는 기능.
//        binding.recyclerView.setHasFixedSize(true);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.recyclerView.setAdapter(new ProductSearchAdapt());
//
//        binding.btnSearch.setOnClickListener(v -> {
//            if (queryImageBitmap != null) {
//                try {
//                    searchByImage(queryImageBitmap);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                Toast.makeText(requireContext(), "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void searchByImage(Bitmap queryImage) throws JSONException {
//        binding.progressBar.setVisibility(View.VISIBLE);
//        apiClient.annotateImage(queryImage)
//                .addOnSuccessListener(this::showSearchResult)
//                .addOnFailureListener(error -> {
//                    Log.e(TAG, "Error calling Vision API Product Search.", error);
//                    showErrorResponse(error.getLocalizedMessage());
//                });
//    }
//
//    private void showSearchResult(List<ProductSearchResult> result) {
//        binding.progressBar.setVisibility(View.GONE);
//        ((ProductSearchAdapt) binding.recyclerView.getAdapter()).submitList(result);
//    }
//
//    private void showErrorResponse(String message) {
//        binding.progressBar.setVisibility(View.GONE);
//        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
//    }
//}


//package com.example.scanbox;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.scanbox.databinding.FragmentProductSearchBinding;
//
//import java.io.InputStream;
//import java.util.List;
//
//public class ProductSearchFragment extends Fragment {
//
//    public static final String TAG = "ProductSearchFragment";
//    private FragmentProductSearchBinding binding;
//    private ProductSearchAPIClient apiClient;
//    private Bitmap queryImageBitmap;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = FragmentProductSearchBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        apiClient = new ProductSearchAPIClient(requireContext());
//
//        if (getArguments() != null) {
//            Uri queryImageUri = getArguments().getParcelable("queryImage");
//            if (queryImageUri != null) {
//                try {
//                    InputStream inputStream = requireContext().getContentResolver().openInputStream(queryImageUri);
//                    queryImageBitmap = BitmapFactory.decodeStream(inputStream);
//                    binding.ivQueryImage.setImageBitmap(queryImageBitmap);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        initViews();
//    }
//
//    private void initViews() {
//        binding.recyclerView.setHasFixedSize(true);
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.recyclerView.setAdapter(new ProductSearchAdapt());
//
//        binding.btnSearch.setOnClickListener(v -> {
//            String query = binding.etQuery.getText().toString().trim();
//            if (!query.isEmpty()) {
//                searchByQuery(query);
//            } else {
//                Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void searchByQuery(String query) {
//        binding.progressBar.setVisibility(View.VISIBLE);
//        apiClient.searchImage(query)
//                .addOnSuccessListener(this::showSearchResult)
//                .addOnFailureListener(error -> {
//                    Log.e(TAG, "Error calling Custom Search API.", error);
//                    showErrorResponse(error.getLocalizedMessage());
//                });
//    }
//
//    private void showSearchResult(List<ProductSearchResult> result) {
//        binding.progressBar.setVisibility(View.GONE);
//        ((ProductSearchAdapt) binding.recyclerView.getAdapter()).submitList(result);
//    }
//
//    private void showErrorResponse(String message) {
//        binding.progressBar.setVisibility(View.GONE);
//        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
//    }
//}


package com.example.scanbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.scanbox.databinding.FragmentProductSearchBinding;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

public class ProductSearchFragment extends Fragment {

    public static final String TAG = "ProductSearchFragment";
    private FragmentProductSearchBinding binding;
    private ProductSearchAPIClient apiClient;
    private Bitmap queryImageBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiClient = new ProductSearchAPIClient(requireContext());

        if (getArguments() != null) {
            Uri queryImageUri = getArguments().getParcelable("queryImage");
            if (queryImageUri != null) {
                try {
                    InputStream inputStream = requireContext().getContentResolver().openInputStream(queryImageUri);
                    queryImageBitmap = BitmapFactory.decodeStream(inputStream);
                    binding.ivQueryImage.setImageBitmap(queryImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        initViews();
    }

    private void initViews() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(new ProductSearchAdapt());

        binding.btnSearch.setOnClickListener(v -> {
            if (queryImageBitmap != null) {
                try {
                    searchByImage(queryImageBitmap);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(requireContext(), "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchByImage(Bitmap queryImage) throws JSONException {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiClient.searchByImage(queryImage)
                .addOnSuccessListener(this::showSearchResult)
                .addOnFailureListener(error -> {
                    Log.e(TAG, "Error calling Vision API or Custom Search API.", error);
                    showErrorResponse(error.getLocalizedMessage());
                });
    }

    private void showSearchResult(List<ProductSearchResult> result) {
        binding.progressBar.setVisibility(View.GONE);
        ((ProductSearchAdapt) binding.recyclerView.getAdapter()).submitList(result);
    }

    private void showErrorResponse(String message) {
        binding.progressBar.setVisibility(View.GONE);
        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}