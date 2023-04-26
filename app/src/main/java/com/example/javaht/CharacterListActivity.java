package com.example.javaht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CharacterListActivity extends AppCompatActivity {

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_list);

        backButton = findViewById(R.id.CharacterListBack);
    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(CharacterListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void addCharacterPage(View view){
        Intent intent = new Intent(CharacterListActivity.this, AddCharacterActivity.class);
        startActivity(intent);

    }

}