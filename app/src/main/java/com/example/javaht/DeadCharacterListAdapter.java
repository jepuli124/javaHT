package com.example.javaht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeadCharacterListAdapter extends RecyclerView.Adapter<DeadCharacterViewHolder> {

    private Context context;


    public DeadCharacterListAdapter(Context context)    {
        this.context = context;
    }

    @NonNull
    @Override
    public DeadCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeadCharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.dead_character_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeadCharacterViewHolder holder, int position) {
        holder.CharacterName.setText(Graveyard.getInstance().getCharacters().get(position).getName());
        holder.CharacterLevel.setText("Taso: " + Integer.toString(Graveyard.getInstance().getCharacters().get(position).getLevel()));
        holder.WinAmount.setText(Integer.toString(Graveyard.getInstance().getCharacters().get(position).getBattlesWon()));
    }

    @Override
    public int getItemCount() {
        return Graveyard.getInstance().getCharacters().size();
    }
}
