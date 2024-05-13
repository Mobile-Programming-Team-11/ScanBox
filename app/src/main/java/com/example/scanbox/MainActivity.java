package com.example.scanbox;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

//    ImageView imageView;
    private String imageFilePath;
    private Uri photoUri;
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    Button scanBtn;
    Button loadBtn;
    Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // 앱 시작 시 DisplayImageFragment를 로드
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DisplayImageFragment())
                    .commit();
        }

//        imageView = findViewById(R.id.camera); // 이 ID가 layout 파일 내의 ImageView ID와 일치하는지 확인
//        if (imageView == null) {
//            Log.e("MainActivity", "ImageView is not initialized!");
//        }
        //TedPermission: 안드로이드에서 권한 허용 여부를 물어보는 외부 라이브러리.
        TedPermission.with(getApplicationContext())
                        .setPermissionListener(permissionListener)
                                .setRationaleMessage("카메라 권한이 필요합니다")
                                        .setDeniedMessage("거부하셨습니다")
                                                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
                                                        .check();

        findViewById(R.id.scanBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scan 버튼 클릭 시 카메라가 켜지도록
                //intent를 통해 기본 내장된 카메라를 띄움
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile =  null;
                    try {
                        photoFile = createImageFile();
                    }catch (IOException ignored){
                        ignored.printStackTrace();
                        throw new RuntimeException("에러 발생");
                    }
                    if(photoFile != null){
                        photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.scanbox.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityResult.launch(intent);
                    }
                }
            }
        });

//        ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            // 카메라 앱에서 반환 후 사진을 갤러리에 저장
//                            saveImageToGallery();  // 여기서 저장하는 것이 적절
//                        }
//                    }
//                });

        loadBtn = findViewById(R.id.loadBtn);
        //load 버튼 클릭 시 갤러리를 불러와 원하는 사진을 imageView에 출력.
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startGalleryResult.launch(intent);
            }
        });

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            //search 버튼 클릭
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void saveImageToGallery() {
        Log.e("imageurl={}", imageFilePath);
        //사진을 갤러리에 저장하는 함수. scanBtn에서 사용
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES); // 사진 폴더에 저장

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
    private File createImageFile() throws IOException{
        //이미지 파일을 저장할 때, 시간 단위로 이미지를 표시하여 중복되지 않게 저장하는 역할.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Test" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, ".jpg", storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

//    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
//        new ActivityResultContracts.StartActivityForResult(),
//        new ActivityResultCallback<ActivityResult>() {
//            private Uri photoUri;
//
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    Uri selectedImageUri;
//                    if (result.getData() != null && result.getData().getData() != null) {
//                        selectedImageUri = result.getData().getData();
//                    } else {
//                        selectedImageUri = this.photoUri; // 카메라를 사용한 경우
//                    }
//                    displayImageFragment(selectedImageUri);
//                }
//            }
//        }
//    );
ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // 카메라에서 사진을 찍고 돌아온 경우
                    Uri imageUri = photoUri; // 카메라 앱에서 저장한 사진의 위치
                    displayImageFragment(imageUri);
                    saveImageToGallery(); // 선택적으로 갤러리에 사진을 저장
                }
            }
        }
);

    private void displayImageFragment(Uri imageUri) {
        DisplayImageFragment fragment = new DisplayImageFragment();
        fragment.setImageUri(imageUri); // 이미지 URI 전달

        // 프래그먼트 트랜잭션을 통해 DisplayImageFragment를 fragment_container에 표시
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
//    ActivityResultLauncher<Intent> startGalleryResult = registerForActivityResult(
//            // loadBtn을 이용한 갤러리에서 이미지 가져오기
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//                        Uri imageUri = result.getData().getData();
//                        try {
//                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                            imageView.setImageBitmap(bitmap);
//                        } catch (IOException e) {
//                            Toast.makeText(MainActivity.this, "이미지 로드 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            });
ActivityResultLauncher<Intent> startGalleryResult = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData(); // 갤러리에서 선택한 이미지의 URI를 가져옴
                    displayImageFragment(imageUri); // DisplayImageFragment에 URI 전달하여 이미지 표시
                } else {
                    Toast.makeText(MainActivity.this, "이미지 선택 취소 또는 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }
        });

    private int exifOrientationToDegress(int exifOrientation) {
        //사진 촬영한 화면을 자동으로 회전시키는 기능
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        } else if ((exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)) {
            return 270;
        }
        return 0;
    }
    private Bitmap rotate(Bitmap bitmap, int exifDegree) {
        //사진을 비트맵으로 변환해주기
        Matrix matrix = new Matrix();
        matrix.postRotate(exifDegree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
    PermissionListener permissionListener = new PermissionListener() {
        //최초 Scan버튼 클릭시 권한 부여 여부 결정
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onPermissionDenied(List<String> deniedPermissions){
            Toast.makeText(getApplicationContext(), "권한이 거부됨", Toast.LENGTH_SHORT).show();
        }
    };
}
