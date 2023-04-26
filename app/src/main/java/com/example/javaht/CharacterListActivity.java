package com.example.javaht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CharacterListActivity extends AppCompatActivity {

    private ImageView backButton;
    private RecyclerView recyclerView;
    private CharacterListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_list);

        backButton = findViewById(R.id.CharacterListBack);
        recyclerView = findViewById(R.id.CharacterRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CharacterListAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(CharacterListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void addCharacterPage(View view){
        Intent intent = new Intent(CharacterListActivity.this, AddCharacterActivity.class);
        startActivity(intent);
    }
    public void chooseFight(View view){
        Intent intent = new Intent(CharacterListActivity.this, ChooseGameModeActivity.class);
        startActivity(intent);
    }
}