package com.example.javaht.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaht.Character;
import com.example.javaht.InfoCharacter;
import com.example.javaht.R;

import java.util.ArrayList;

public class EquipmentFragment extends Fragment {

    TextView txtHelmetName, txtHelmetEffects, txtNecklaceName, txtNecklaceEffects, txtChestplateName, txtChestplateEffects, txtHandName, txtHandEffects;


    public EquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equip_items, container, false);
        txtHelmetName = view.findViewById(R.id.txtHelmetName);
        txtHelmetEffects = view.findViewById(R.id.txtHelmetEffects);
        txtNecklaceName = view.findViewById(R.id.txtNecklaceName);
        txtNecklaceEffects = view.findViewById(R.id.txtNecklaceEffects);
        txtChestplateName = view.findViewById(R.id.txtChestplateName);
        txtChestplateEffects = view.findViewById(R.id.txtChestplateEffects);
        txtHandName = view.findViewById(R.id.txtHandName);
        txtHandEffects = view.findViewById(R.id.txtHandEffects);
        txtHelmetName.setText("Ei kypärää");
        txtHelmetEffects.setText("");
        txtNecklaceName.setText("Ei korua");
        txtNecklaceEffects.setText("");
        txtChestplateName.setText("Ei panssaria");
        txtChestplateEffects.setText("");
        txtHandName.setText("Ei asetta");
        txtHandEffects.setText("");

        Character character = InfoCharacter.getInstance().getCharacter();
        /*
        for(ItemSlot itemSlot:character.getItems()){
            String txt = "";
            if(!itemSlot.isEmpty()){
                switch (itemSlot.getSlotType()){
                    case "head":
                        txtHelmetName.setText(itemSlot.getItem().getName());
                        for (StatChange effect: itemSlot.getItem().getEffects()){
                            txt += effect.getName() + ": " + effect.getValue() + " lv";
                        }
                        txtHelmetEffects.setText(txt);
                        break;
                    case "torso":
                        txtChestplateName.setText(itemSlot.getItem().getName());
                        for (StatChange effect: itemSlot.getItem().getEffects()){
                            txt += effect.getName() + ": " + effect.getValue() + " lv";
                        }
                        txtChestplateEffects.setText(txt);
                        break;
                    case "necklace":
                        txtNecklaceName.setText(itemSlot.getItem().getName());
                        for (StatChange effect: itemSlot.getItem().getEffects()){
                            txt += effect.getName() + ": " + effect.getValue() + " lv";
                        }
                        txtNecklaceEffects.setText(txt);
                        break;
                    case "hand":
                        txtHandName.setText(itemSlot.getItem().getName());
                        for (StatChange effect: itemSlot.getItem().getEffects()){
                            txt += effect.getName() + ": " + effect.getValue() + " lv";
                        }
                        txtHandEffects.setText(txt);
                        break;
                    default:
                        break;
                }
            }
        }*/

        return view;
    }
}
