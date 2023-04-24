package com.example.javaht;

import java.util.ArrayList;

public class CharacterStorage {
    ArrayList<Character> characters = new ArrayList<>();

    public CharacterStorage() {
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void addCharacter(Character character){
        characters.add(character);
    }

}
