package com.example.javaht;

import java.util.ArrayList;

public class CharacterStorage {
    private ArrayList<Character> characters = new ArrayList<>();

    private static CharacterStorage characterStorage = null;
    public CharacterStorage() {
    }

    public static CharacterStorage getInstance(){
        if(characterStorage == null){
            characterStorage = new CharacterStorage();
        }
        return characterStorage;
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


}
