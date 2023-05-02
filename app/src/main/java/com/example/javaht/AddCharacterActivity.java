package com.example.javaht;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCharacterActivity extends AppCompatActivity {

    private TextView characterName, health, attack, defense, info;
    private ImageView btnBack;
    private RadioButton image1, image2, image3;
    private RadioGroup rgImages;
    int imageID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_character_page);

        characterName = findViewById(R.id.txtCharacterName);
        btnBack = findViewById(R.id.btnToCharacterList);
        health = findViewById(R.id.nHealth);
        attack = findViewById(R.id.nAttack);
        defense = findViewById(R.id.nDefense);
        info = findViewById(R.id.txtInfoAddCharacter);
        rgImages = findViewById(R.id.rgChooseImage);
        health.setText("");
        attack.setText("");
        defense.setText("");


        info.setText("Voi antaa hahmollesi " + Character.getPointsToBeAssignedOnCreation() + "\n" +
                "pistettä aloitus tasoiksi.\n" +
                "Ylimääräiset pisteet\n" +
                "asetetaan satunnaisesti");

    }

    public void returnToCharacterList(View view){
        Intent intent = new Intent(AddCharacterActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void checkRadioGroupStatus(View view)    {
        switch (rgImages.getCheckedRadioButtonId()) {
            case R.id.rB1:
                imageID = R.drawable.armor_1300179_1280;
                break;
            case R.id.rB2:
                imageID = R.drawable.cartoon_1292998_640;
                break;
            case R.id.rB3:
                imageID = R.drawable.king_37240_640;
                break;
        }
    }

    public void addCharacter(View view){
        if (characterName.getText().toString().length() <= 2 ){
            Toast.makeText(this, "Anna hahmolle ainakin 3 kirjaimen pituinen nimi", Toast.LENGTH_SHORT).show();
            return;
        }
        int healthInt = 0;
        int attackInt = 0;
        int defenseInt = 0;

        if(health.getText().toString().length() >= 1){
            healthInt += Integer.parseInt(health.getText().toString());
        }
        if(attack.getText().toString().length() >= 1){
            attackInt += Integer.parseInt(attack.getText().toString());
        }
        if(defense.getText().toString().length() >= 1){
            defenseInt += Integer.parseInt(defense.getText().toString());
        }


        if(healthInt+attackInt+defenseInt > Character.getPointsToBeAssignedOnCreation()){
            Toast.makeText(this, "Antamasi tasot ovat liian suuret", Toast.LENGTH_SHORT).show();
            return;
        }

        health.setText("");
        attack.setText("");
        defense.setText("");

        checkRadioGroupStatus(view);
        Character character = new Character(characterName.getText().toString(), healthInt, attackInt, defenseInt);
        character.setImage(imageID);
        CharacterStorage.getInstance().addCharacter(character, view.getContext());
        characterName.setText("");
        Toast.makeText(this, "Character Added", Toast.LENGTH_SHORT).show();
        CharacterStorage.getInstance().saveCharacters(view.getContext());
    }

}
