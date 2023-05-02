package com.example.javaht;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView itemName, itemEffects;
    ImageView image;
    Button equip, discard;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.txtItemName);
        itemEffects = itemView.findViewById(R.id.txtItemEffects);
        image = itemView.findViewById(R.id.imgItem);

        equip = itemView.findViewById(R.id.btnEquipToCharacter);
        discard = itemView.findViewById(R.id.btnBurnToXp);

    }
}
