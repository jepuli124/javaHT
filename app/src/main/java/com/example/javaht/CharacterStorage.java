package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CharacterStorage {
    private ArrayList<Character> characters = new ArrayList<>();
    private int mode = 0;
    private Character mainFighter = null;
    private Character enemyFighter = null;

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

    public void killCharacter(int id){
        Graveyard.getInstance().addCharacter(characters.get(id));
        characters.remove(id);
    }

    public Character getMainFighter() {
        return mainFighter;
    }

    public void setMainFighter(Character mainFighter) {
        this.mainFighter = mainFighter;
    }

    public Character getEnemyFighter() {
        return enemyFighter;
    }

    public void setFighter(Character character){
        if (mode == 0){
            setMainFighter(character);
        }
        else if (mode == 1){
            setEnemyFighter(character);
        }
    }

    public void setEnemyFighter(Character enemyFighter) {
        this.enemyFighter = enemyFighter;
    }

    public void saveCharacters(Context context){
        try {
            ObjectOutputStream OOPS = new ObjectOutputStream(context.openFileOutput("Characters.data", Context.MODE_PRIVATE));
            OOPS.writeObject(characters);
            OOPS.close();
        } catch (IOException e) {
            Toast.makeText(context, "Saving Failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadCharacters(Context context){
        try {
            ObjectInputStream OIPS = new ObjectInputStream(context.openFileInput("Characters.data"));
            characters = (ArrayList<Character>) OIPS.readObject();
            OIPS.close();
        } catch(FileNotFoundException e1) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
        }catch(IOException e2) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }catch(ClassNotFoundException e3) {
            Toast.makeText(context, "Class not found?", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMode(int mode){
        this.mode = mode;
    }

    public int getMode(){
        return mode;
    }


}
