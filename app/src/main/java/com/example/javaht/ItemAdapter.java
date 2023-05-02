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

    private ArrayList<Item> items;

    public ItemAdapter(Context context,  ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.equipment_view, parent, false));

    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){

        holder.itemName.setText(items.get(position).getName());
        String effects = "";
        for(StatChange effect : items.get(position).getEffects()){
            effects += effect.getName() + ": " + effect.getValue()+"\n";
        }
        holder.itemEffects.setText(effects);
        int pos = holder.getAdapterPosition();
        switch (InfoCharacter.getInstance().getCharacter().getItemStorage().get(pos).getSlotType()){
            case "hand":
                holder.image.setImageResource(R.drawable.sworddalle);
                break;
            case "torso":
                holder.image.setImageResource(R.drawable.chestplate);
                break;
            case "head":
                holder.image.setImageResource(R.drawable.helmetdalle);
                break;
            case "necklace":
                holder.image.setImageResource(R.drawable.necklace);
                break;
            default:
                holder.image.setImageResource(R.drawable.trophy_305554_640);
                break;
        }
        holder.discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                //InfoCharacter.getInstance().getCharacter().getItemStorage()
                InfoCharacter.getInstance().getCharacter().getItemStorage().remove(pos);
                notifyItemRemoved(pos);
                CharacterStorage.getInstance().saveCharacters(view.getContext());
            }
        });

        holder.equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                InfoCharacter.getInstance().getCharacter().setItem(pos);
                InfoCharacter.getInstance().getCharacter().getItemStorage().remove(pos);
                notifyDataSetChanged();
                CharacterStorage.getInstance().saveCharacters(view.getContext());
            }
        });
    }

    @Override
    public int getItemCount(){
        return items.size();
    }
}
