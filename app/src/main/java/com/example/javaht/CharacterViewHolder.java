package com.example.javaht;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterViewHolder extends RecyclerView.ViewHolder{
    TextView characterName, characterLevel;
    ImageView characterImage, imgDel;
    Button chooseButton;


    public CharacterViewHolder(@NonNull View itemView) {
        super(itemView);
        characterName = itemView.findViewById(R.id.textViewName);
        characterLevel = itemView.findViewById(R.id.textViewLevel);
        characterImage = itemView.findViewById(R.id.imageViewCharacter);
        imgDel = itemView.findViewById(R.id.imgDel);
        chooseButton = itemView.findViewById(R.id.ChooseButton);
    }
}
