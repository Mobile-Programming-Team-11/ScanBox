package com.example.scanbox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

public class CropImg extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 0; // 앨범에서 사진 잘 가져왔을 때의 result code
    private Button btnPictureCrop;
    private ImageView imageView;
    private Uri uri; // 이미지 파일 경로

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnPictureCrop = findViewById(R.id.btnPictureCrop);
        //imageView = findViewById(R.id.imageView);

        // 권한 처리
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> Log.d("mmm", "허용된 권한 갯수 : " + permissions.size()))
                .onDenied(permissions -> Log.d("mmm", "거부된 권한 갯수 : " + permissions.size()))
                .start();

        // 버튼 누르면 사진 가져오기
        btnPictureCrop.setOnClickListener(v -> getFromAlbum());
    }

    // 갤러리 이미지 선택해서 가져오기
    private void getFromAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*"); // 모든 이미지
        startActivityForResult(intent, PICK_FROM_ALBUM);
        Log.d("mmm ddk", String.valueOf(CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE));
    }

    // 사진 크롭하기
    private void cropImage(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON) // 크롭 위한 가이드 열어서 크롭할 이미지 받아오기
                .setCropShape(CropImageView.CropShape.RECTANGLE) // 사각형으로 자르기
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 이미지 잘 선택했으면
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_ALBUM:
                    if (Build.VERSION.SDK_INT >= 19) {
                        uri = data.getData(); // 선택한 이미지의 주소
                        // 사용자가 이미지를 선택했으면(null이 아니면)
                        if (uri != null) {
                            cropImage(uri);
                        }
                    }
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null) {
                        Uri resultUri = result.getUri();
                        if (resultUri != null) {
                            try {
                                // 이미지 파일 읽어와서 설정하기
                                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
                                imageView.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
    }
}

