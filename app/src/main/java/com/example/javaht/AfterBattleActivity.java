package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AfterBattleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int result = bundle.getInt("result");
        if (result == 1) {
            setContentView(R.layout.victory_screen);
        } else {
            setContentView(R.layout.defeat_screen);
        }
    }

    


    public void backToCharacterListActivity(View view){
        Intent intent = new Intent(AfterBattleActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

}
