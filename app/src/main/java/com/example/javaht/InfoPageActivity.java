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
        Character character = InfoCharacter.getInstance().getCharacter();

        info.setText("\n   HP: "+ character.getStatByName("Health").getLevel() +
                " \n\nATTACK: "+ character.getStatByName("Attack").getLevel()
                +" \n\nDEFENSE: "+ character.getStatByName("Defense").getLevel()
                /*+"\n\nITEMS: " + character.getItems().get(0).getItem().getName()
                +"\n" + character.getItems().get(1).getItem().getName()
                +"\n" + character.getItems().get(2).getItem().getName()
                +"\n" + character.getItems().get(3).getItem().getName()*/
                +"\n\nLEVEL: " + character.getLevel()
                +"\n\nBATTLES FOUGHT: " + character.getBattlesFought()
                + "\n\nWINS: " + character.getBattlesWon());

    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(InfoPageActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }


    public void changeLayoutToItemView(View view)   {
        Intent intent = new Intent(InfoPageActivity.this, EquipmentActivity.class);
        startActivity(intent);
    }
}