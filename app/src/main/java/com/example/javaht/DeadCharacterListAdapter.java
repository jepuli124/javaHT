package com.example.javaht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeadCharacterListAdapter extends RecyclerView.Adapter<DeadCharacterViewHolder> {

    private Context context;
    private ArrayList<Character> deadList = new ArrayList<>();

    public DeadCharacterListAdapter(Context context, ArrayList<Character> deadList)    {
        this.context = context;
        this.deadList = deadList;
    }

    @NonNull
    @Override
    public DeadCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeadCharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.dead_character_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeadCharacterViewHolder holder, int position) {
        holder.CharacterName.setText(deadList.get(position).getName());
        holder.CharacterLevel.setText("Taso: " + Integer.toString(deadList.get(position).getLevel()));
        holder.WinAmount.setText(Integer.toString(deadList.get(position).getBattlesWon()));
    }

    @Override
    public int getItemCount() {
        return deadList.size();
    }
}
