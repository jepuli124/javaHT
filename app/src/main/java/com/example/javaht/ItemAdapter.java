package com.example.javaht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{
    private Context context;

    private Character character;

    public ItemAdapter(Context context,  Character character) {
        this.context = context;
        this.character = character;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.equipment_view, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){

        holder.itemName.setText(character.getItemStorage().get(position).getName());
        String effects = "";
        for(StatChange effect : character.getItemStorage().get(position).getEffects()){
            effects += effect.getName() + ": " + effect.getValue()+"\n";
        }
        holder.itemEffects.setText(effects);

        holder.discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                //Storage.getInstance().delProduct(pos);
                notifyItemRemoved(pos);
            }
        });

        holder.equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();


                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount(){
        return character.getItemStorage().size();
    }
}
