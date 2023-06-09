package com.example.javaht.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javaht.Character;
import com.example.javaht.InfoCharacter;
import com.example.javaht.ItemSlot;
import com.example.javaht.R;
import com.example.javaht.StatChange;

public class CharacterInfoFragment extends Fragment {

    private TextView info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_character_info, container, false);

        info = view.findViewById(R.id.infoText);
        Character character = InfoCharacter.getInstance().getCharacter();

        info.setText("\n   HP: "+ character.getStatByName("Health").getLevel() +
                " \n\nATTACK: "+ character.getStatByName("Attack").getLevel()
                +" \n\nDEFENSE: "+ character.getStatByName("Defense").getLevel()
                +"\n\nLEVEL: " + character.getLevel()
                +"\n\nXP:"+ character.getXp()
                +"\n\nBATTLES FOUGHT: " + character.getBattlesFought()
                + "\n\nWINS: " + character.getBattlesWon());


        return view;
    }

    public void onResume() {
        super.onResume();
        Character character = InfoCharacter.getInstance().getCharacter();
        info.setText("\n   HP: "+ character.getStatByName("Health").getLevel() +
                " \n\nATTACK: "+ character.getStatByName("Attack").getLevel()
                +" \n\nDEFENSE: "+ character.getStatByName("Defense").getLevel()
                +"\n\nLEVEL: " + character.getLevel()
                +"\n\nXP:"+ character.getXp()
                +"\n\nBATTLES FOUGHT: " + character.getBattlesFought()
                + "\n\nWINS: " + character.getBattlesWon());
    }
}