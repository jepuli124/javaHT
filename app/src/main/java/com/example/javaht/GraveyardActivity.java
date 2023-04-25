package com.example.javaht;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GraveyardActivity extends AppCompatActivity {

    private ImageView backButton;
    private RecyclerView recyclerView;
    private DeadCharacterListAdapter adapter;
    private Graveyard graveStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graveyard_page);

        backButton = findViewById(R.id.GraveyardBack);
        recyclerView = findViewById(R.id.DeadRecyclerView);
        graveStorage = Graveyard.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DeadCharacterListAdapter(getApplicationContext(), graveStorage.getCharacters());
        recyclerView.setAdapter(adapter);
    }

    public void changeLayoutToHomePage(View view)   {
        Intent intent = new Intent(GraveyardActivity.this, MainActivity.class);
        startActivity(intent);
    }
}