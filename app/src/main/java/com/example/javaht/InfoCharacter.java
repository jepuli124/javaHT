package com.example.javaht;

public class InfoCharacter {
    private Character character;

    private static InfoCharacter infoCharacter = null;
    private InfoCharacter() {
    }

    public static InfoCharacter getInstance(){
        if(infoCharacter == null){
            infoCharacter = new InfoCharacter();
        }
        return infoCharacter;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
