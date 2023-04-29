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
        int pos = holder.getAdapterPosition();

        holder.discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();

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
        return items.size();
    }
}
