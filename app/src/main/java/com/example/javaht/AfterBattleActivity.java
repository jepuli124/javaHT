package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AfterBattleActivity extends AppCompatActivity {

    TextView characterName, characterVictories;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int result = bundle.getInt("result");
        if (result == 1) {
            setContentView(R.layout.victory_screen);
            characterName = findViewById(R.id.txtCharacterNameVic);
            characterVictories = findViewById(R.id.txtCharacterVictories);
        } else {
            setContentView(R.layout.defeat_screen);
            characterName = findViewById(R.id.txtCharacterNameDef);
            characterVictories = findViewById(R.id.txtCharacterVictoriesDef);
        }
        characterName.setText(bundle.getString("name"));
        characterVictories.setText(Integer.toString(bundle.getInt("victories")));
    }




    public void backToCharacterListActivity(View view){
        Intent intent = new Intent(AfterBattleActivity.this, CharacterListActivity.class);
        CharacterStorage.getInstance().setMainFighter(null);
        CharacterStorage.getInstance().setEnemyFighter(null);
        startActivity(intent);
    }

    public void backToFight(View view){
        Intent intent = new Intent(AfterBattleActivity.this, BattleActivity.class);
        startActivity(intent);
    }



}
