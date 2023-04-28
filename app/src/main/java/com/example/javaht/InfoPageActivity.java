package com.example.javaht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoPageActivity extends AppCompatActivity {

    private TextView info;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        info = findViewById(R.id.infoText);
        backBtn = findViewById(R.id.infoPageBackButton);

        info.setText("\n   HP: \n\nATTACK: \n\nITEMS: \n\nLEVEL: \n\nWINS: ");

    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(InfoPageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}