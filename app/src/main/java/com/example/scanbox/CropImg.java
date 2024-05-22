//package com.example.scanbox;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;
//
//public class CropImg extends AppCompatActivity {
//    private static final int PERMISSION_REQUEST_CODE = 1001;
//    private Button btnPictureCrop;
//    private ImageView imageView;
//    private Uri uri; // 이미지 파일 경로
//
//    // ActivityResultLauncher 초기화
//    private final ActivityResultLauncher<Intent> getImageLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null && data.getData() != null) {
//                            uri = data.getData();
//                            cropImage(uri);
//                        }
//                    }
//                }
//            }
//    );
//
//    private final ActivityResultLauncher<Intent> cropImageLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        CropImage.ActivityResult cropResult = CropImage.getActivityResult(data);
//                        if (cropResult != null && cropResult.getUri() != null) {
//                            Uri resultUri = cropResult.getUri();
//                            try {
//                                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
//                                imageView.setImageBitmap(bitmap);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//    );
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnPictureCrop = findViewById(R.id.searchBtn);
////        imageView = findViewById(R.id.imageView);
//
//        // 권한 요청
//        requestPermissions();
//
//        // 버튼 누르면 사진 가져오기
//        btnPictureCrop.setOnClickListener(v -> getFromAlbum());
//    }
//
//    private void requestPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 이상
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{
//                                Manifest.permission.READ_MEDIA_IMAGES,
//                                Manifest.permission.READ_MEDIA_VIDEO,
//                                Manifest.permission.READ_MEDIA_AUDIO
//                        },
//                        PERMISSION_REQUEST_CODE);
//            }
//        } else {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this,
//                        new String[]{
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        },
//                        PERMISSION_REQUEST_CODE);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            for (int result : grantResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    Log.d("Permission", "권한 거부됨");
//                    return;
//                }
//            }
//            Log.d("Permission", "모든 권한 허용됨");
//        }
//    }
//
//    // 갤러리 이미지 선택해서 가져오기
//    private void getFromAlbum() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*"); // 모든 이미지
//        getImageLauncher.launch(intent);
//        Log.d("mmm ddk", String.valueOf(CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE));
//    }
//
//    // 사진 크롭하기
//    private void cropImage(Uri uri) {
//        CropImage.activity(uri)
//                .setGuidelines(CropImageView.Guidelines.ON) // 크롭 위한 가이드 열어서 크롭할 이미지 받아오기
//                .setCropShape(CropImageView.CropShape.RECTANGLE) // 사각형으로 자르기
//                .start(this);
//    }
//}