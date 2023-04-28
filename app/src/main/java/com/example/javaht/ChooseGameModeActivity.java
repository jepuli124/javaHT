package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseGameModeActivity extends AppCompatActivity {

    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode_menu);

    }

    @Override
    protected void onStop(){
        super.onStop();
        CharacterStorage.getInstance().setMode(mode);

    }

    public void changeLayoutToSurvival(View view)   {
        Intent intent = new Intent(ChooseGameModeActivity.this, BattleActivity.class);
        startActivity(intent);
    }

    public void changeLayoutToTrain(View view)   {
        this.mode = 1;
        Intent intent = new Intent(ChooseGameModeActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void back(View view){
        Intent intent = new Intent(ChooseGameModeActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

}
