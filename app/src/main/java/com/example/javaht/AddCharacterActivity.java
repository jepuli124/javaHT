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

    private TextView characterName;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_character_page);

        characterName = findViewById(R.id.txtCharacterName);
        btnBack = findViewById(R.id.btnToCharacterList);
    }

    public void returnToCharacterList(View view){
        Intent intent = new Intent(AddCharacterActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void addCharacter(View view){
        if (characterName.getText().toString().length() <= 2 ){
            Toast.makeText(this, "Please give character at least 3 character long name", Toast.LENGTH_SHORT).show();
            return;
        }
        Character character = new Character(characterName.getText().toString());
        CharacterStorage.getInstance().addCharacter(character, view.getContext());
        characterName.setText("");
        Toast.makeText(this, "Character Added", Toast.LENGTH_SHORT).show();
        CharacterStorage.getInstance().saveCharacters(view.getContext());
    }

}
