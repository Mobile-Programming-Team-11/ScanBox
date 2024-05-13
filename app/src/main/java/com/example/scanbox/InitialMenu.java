package com.example.scanbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialMenu extends AppCompatActivity{
    Button startBtn;

    // 앱 시작 시 화면. 추후 앱 UI 개선 필요.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_menu);

        startBtn = findViewById(R.id.openBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
