package com.example.javaht;

import java.util.ArrayList;

public class Graveyard {
    private ArrayList<Character> characters = new ArrayList<>();

    private static Graveyard graveyard = null;

     public Graveyard() {
     }
     public ArrayList<Character> getCharacters() {
         return characters;
     }

     public void addCharacter(Character character){
         characters.add(character);
     }

    public void removeCharacter(int id){
        characters.remove(id);
    }

    public static Graveyard getInstance(){
         if(graveyard == null){
             graveyard = new Graveyard();
         }
         return graveyard;
     }

    }


