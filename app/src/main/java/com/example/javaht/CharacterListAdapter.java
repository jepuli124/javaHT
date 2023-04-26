package com.example.javaht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterViewHolder> {
    private Context context;


    public CharacterListAdapter(Context context)    {
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.character_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.characterName.setText(CharacterStorage.getInstance().getCharacters().get(position).getName());
        holder.characterLevel.setText("Taso: " + Integer.toString(CharacterStorage.getInstance().getCharacters().get(position).getLevel()));
        holder.characterImage.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return CharacterStorage.getInstance().getCharacters().size();
    }
}
