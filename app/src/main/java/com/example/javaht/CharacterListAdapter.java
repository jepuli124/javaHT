package com.example.javaht;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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
                CharacterStorage.getInstance().saveCharacters(view.getContext());
                notifyItemRemoved(pos);
            }
        });
        holder.chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                CharacterStorage.getInstance().setFighter(CharacterStorage.getInstance().getCharacters().get(pos));
                if(CharacterStorage.getInstance().getMode() == 0){
                    CharacterStorage.getInstance().removeCharacter(pos);
                    Intent intent = new Intent(view.getContext(), ChooseGameModeActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else{
                    CharacterStorage.getInstance().setMode(0);
                    Intent intent = new Intent(view.getContext(), BattleActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return CharacterStorage.getInstance().getCharacters().size();
    }
}