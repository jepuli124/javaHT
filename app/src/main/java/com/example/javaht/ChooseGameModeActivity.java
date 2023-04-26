package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseGameModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamemode_menu);

    }

    public void changeLayoutToSurvial(View view)   {
        Intent intent = new Intent(ChooseGameModeActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void changeLayoutToTrain(View view)   {
        //Tällä voi testata listan toimivuus:
        //graveStorage.getCharacters().add(newCharacter);

        Intent intent = new Intent(ChooseGameModeActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void back(View view){
        Intent intent = new Intent(ChooseGameModeActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

}
