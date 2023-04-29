package com.example.javaht;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCharacterActivity extends AppCompatActivity {

    private TextView characterName, health, attack, defense;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_character_page);

        characterName = findViewById(R.id.txtCharacterName);
        btnBack = findViewById(R.id.btnToCharacterList);
        health = findViewById(R.id.nHealth);
        attack = findViewById(R.id.nAttack);
        defense = findViewById(R.id.nDefense);
        health.setText("");
        attack.setText("");
        defense.setText("");
    }

    public void returnToCharacterList(View view){
        Intent intent = new Intent(AddCharacterActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void addCharacter(View view){
        if (characterName.getText().toString().length() <= 2 ){
            Toast.makeText(this, "Anna hahmolle ainakin 3 kirjaimen pituinen nimi", Toast.LENGTH_SHORT).show();
            return;
        }
        int healthInt = 0;
        int attackInt = 0;
        int defenseint = 0;

        if(health.getText().toString().length() >= 1){
            healthInt += Integer.parseInt(health.getText().toString());
        }
        if(health.getText().toString().length() >= 1){
            attackInt += Integer.parseInt(attack.getText().toString());
        }
        if(health.getText().toString().length() >= 1){
            defenseint += Integer.parseInt(defense.getText().toString());
        }


        if(healthInt+attackInt+defenseint > 8){
            Toast.makeText(this, "Antamasi tasot ovat liian suuret", Toast.LENGTH_SHORT).show();
            return;
        }

        health.setText("");
        attack.setText("");
        defense.setText("");

        Character character = new Character(characterName.getText().toString(), healthInt, attackInt, defenseint);
        CharacterStorage.getInstance().addCharacter(character, view.getContext());
        characterName.setText("");
        Toast.makeText(this, "Character Added", Toast.LENGTH_SHORT).show();
        CharacterStorage.getInstance().saveCharacters(view.getContext());
    }

}
