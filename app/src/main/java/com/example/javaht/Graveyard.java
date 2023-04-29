package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Graveyard {
    private ArrayList<Character> characters = new ArrayList<>();

    private static Graveyard graveyard = null;

     public Graveyard() {
     }
     public ArrayList<Character> getCharacters() {
         return characters;
     }

     public void addCharacter(Character character, Context context){
         characters.add(character);
         saveDeadCharacters(context);
     }

    public void removeCharacter(int id, Context context){
         characters.remove(id);
         saveDeadCharacters(context);

    }

    public static Graveyard getInstance(){
         if(graveyard == null){
             graveyard = new Graveyard();
         }
         return graveyard;
     }
    public void saveDeadCharacters(Context context){
        try {
            ObjectOutputStream OOPS = new ObjectOutputStream(context.openFileOutput("DeadCharacters.data", Context.MODE_PRIVATE));
            OOPS.writeObject(characters);
            OOPS.close();
        } catch (IOException e) {
            Toast.makeText(context, "Saving Failed", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadDeadCharacters(Context context){
        try {
            ObjectInputStream OIPS = new ObjectInputStream(context.openFileInput("DeadCharacters.data"));
            characters = (ArrayList<Character>) OIPS.readObject();
            OIPS.close();
        }catch (FileNotFoundException e1){
        }catch(IOException e2) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }catch(ClassNotFoundException e3) {
            Toast.makeText(context, "Class not found?", Toast.LENGTH_SHORT).show();
        }
    }

    }


