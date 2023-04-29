package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(CreditsActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
