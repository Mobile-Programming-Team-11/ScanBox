package com.example.scanbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;

public class DisplayImageFragment extends Fragment {

    private ImageView imageView;
//    private Button searchButton;
    private Uri imageUri; // 이미지 URI를 저장할 필드

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        imageView = view.findViewById(R.id.camera); //모든 사진은 여기서 출력됨
//        searchButton = view.findViewById(R.id.button_search);

        if (imageUri != null) { // 이미 URI가 설정되어 있다면
            loadImage(imageUri);
        }

//        searchButton.setOnClickListener(v -> {
//            // 검색 버튼 로직 구현
//        });

        return view;
    }

    // 외부에서 URI를 설정할 수 있는 메서드
    public void setImageUri(Uri uri) {
        this.imageUri = uri;
        if (imageView != null && isAdded()) {
            loadImage(uri); // 프래그먼트가 활성 상태일 때 이미지 로드
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (imageUri != null) {
            loadImage(imageUri); // 뷰가 생성된 후에 이미지 로드
        }
    }

    private void loadImage(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e("DisplayImageFragment", "Image load failed", e);
            Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }
}