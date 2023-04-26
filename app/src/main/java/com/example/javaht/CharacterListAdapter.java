package com.example.javaht;

import android.content.Context;
import android.content.Intent;
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
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                CharacterStorage.getInstance().removeCharacter(pos);
                notifyItemRemoved(pos);
            }
        });
        holder.chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return CharacterStorage.getInstance().getCharacters().size();
    }
}
