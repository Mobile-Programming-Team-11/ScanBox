import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.scanbox"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scanbox"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//    implementation("com.yanzhenjie:permission:2.0.3")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("io.github.ParkSangGwon:tedpermission:2.3.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.gms:play-services-tasks:18.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
}

// 중복된 권한 부여 사용으로 인한 'INSTALL_FAILED_BAD_PERMISSION_GROUP' 발생. 해결책: 중복된 기능의 implementation을 지우자.