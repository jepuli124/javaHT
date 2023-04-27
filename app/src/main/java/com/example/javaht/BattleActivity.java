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
            endActivity(1);
        }
    }

    public void enemyAction(){
        if(battle.attack(battle.getEnemyCharacter(), battle.getPlayerCharacter()) == 1){
            battle.endBattle(0, this);
            endActivity(0);
        }
    }



    public void endActivity(int result){
        Intent intent = new Intent(BattleActivity.this, CharacterListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("result", result);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
