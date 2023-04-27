package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BattleActivity extends AppCompatActivity {

    private Battle battle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_page);
        if (CharacterStorage.getInstance().getEnemyFighter() == null) {
            battle = new Battle(CharacterStorage.getInstance().getMainFighter());
        } else {
            battle = new Battle(CharacterStorage.getInstance().getMainFighter(), CharacterStorage.getInstance().getEnemyFighter());
        }
    }


    public void attack(View view){
        if(battle.attack(battle.getPlayerCharacter(), battle.getEnemyCharacter()) == 1){
            battle.endBattle(1, this);
        }
    }

    public void enemyAction(){
        if(battle.attack(battle.getEnemyCharacter(), battle.getPlayerCharacter()) == 1){
            battle.endBattle(0, this);
        }
    }


    public void back(View view){
        Intent intent = new Intent(BattleActivity.this, CharacterListActivity.class);
        startActivity(intent);
    }
}
