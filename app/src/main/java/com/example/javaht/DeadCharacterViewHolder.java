package com.example.javaht;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeadCharacterViewHolder extends RecyclerView.ViewHolder {

    TextView CharacterName, CharacterLevel, WinAmount;
    ImageView Trophy, Tombstone;

    public DeadCharacterViewHolder(@NonNull View itemView) {
        super(itemView);
        CharacterName = itemView.findViewById(R.id.textViewNameDead);
        CharacterLevel = itemView.findViewById(R.id.textViewLevelDead);
        WinAmount = itemView.findViewById(R.id.textViewWinAmount);
        Trophy = itemView.findViewById(R.id.imageViewTrophy);
        Tombstone = itemView.findViewById(R.id.imageViewTombstone);
    }
}
