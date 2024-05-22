package com.example.scanbox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.Manifest;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private static final int REQUEST_IMAGE_CROP = 673;

    private String imageFilePath;
    private Uri photoUri;
    private Uri croppedImageUri;
    private Button scanBtn;
    private Button loadBtn;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DisplayImageFragment())
                    .commit();
        }

        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다")
                .setDeniedMessage("거부하셨습니다")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
                .check();

        scanBtn = findViewById(R.id.scanBtn);
        loadBtn = findViewById(R.id.loadBtn);
        searchBtn = findViewById(R.id.searchBtn);

        scanBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                    throw new RuntimeException("에러 발생");
                }
                if (photoFile != null) {
                    photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.scanbox.fileprovider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        loadBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_CROP);
        });

        searchBtn.setOnClickListener(v -> {
            if (photoUri != null) {
                cropImage(photoUri);
            } else {
                Toast.makeText(this, "사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImageToGallery() {
        Log.e("imageurl={}", imageFilePath);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                Toast.makeText(this, "사진이 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "저장 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Test" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                displayImageFragment(photoUri);
                saveImageToGallery();
            } else if (requestCode == REQUEST_IMAGE_CROP && data != null) {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    displayImageFragment(imageUri);
                    photoUri = imageUri; // 업데이트된 이미지 Uri를 photoUri에 저장
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (result != null && result.getUri() != null) {
                    croppedImageUri = result.getUri();
                    displayImageFragment(croppedImageUri);
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Toast.makeText(this, "이미지 크롭 실패: " + CropImage.getActivityResult(data).getError(), Toast.LENGTH_SHORT).show();
        }
    }

    private void displayImageFragment(Uri imageUri) {
        DisplayImageFragment fragment = new DisplayImageFragment();
        fragment.setImageUri(imageUri);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();
        }
    };

    private void cropImage(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);
    }
}
