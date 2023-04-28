package com.example.javaht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private Button graveyardButton;
    private Graveyard graveStorage;
    private Character newCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        playButton = findViewById(R.id.PlayButton);
        graveyardButton = findViewById(R.id.GraveyardButton);
        graveStorage = Graveyard.getInstance();
        newCharacter = new Character("Chad");
        loadCharacters();
    }

    public void changeLayoutToCharacterList(View view)   {
        Intent intent = new Intent(MainActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }

    public void changeLayoutToGraveyardPage(View view)   {
        //Tällä voi testata listan toimivuus:
        //graveStorage.getCharacters().add(newCharacter);

        Intent intent = new Intent(MainActivity.this, GraveyardActivity.class);
        startActivity(intent);
    }

    public void loadCharacters(){
        CharacterStorage.getInstance().loadCharacters(this);
        Graveyard.getInstance().loadDeadCharacters(this);
        Toast.makeText(this, "Hahmot ladattu", Toast.LENGTH_SHORT);
    }

}