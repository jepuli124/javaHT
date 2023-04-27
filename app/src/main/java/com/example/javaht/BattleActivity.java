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

    public void startBattle(View view) {



        battle.endBattle(1, this);
        endActivity(1, battle.getPlayerCharacter().getName(), battle.getPlayerCharacter().getBattlesWon());
    }

    public int quickAttack(){
        // same return as Battle.attack()
        int attackResult = battle.attack(battle.getPlayerCharacter(), battle.getEnemyCharacter(), 2, 100); // comparison value 200
        return attackResult;
    }

    public int mediumAttack(){
        // same return as Battle.attack()
        int attackResult = battle.attack(battle.getPlayerCharacter(), battle.getEnemyCharacter(), 3, 80); // comparison value 240
        return attackResult;
    }

    public int heavyAttack(){
        // same return as Battle.attack()
        int attackResult = battle.attack(battle.getPlayerCharacter(), battle.getEnemyCharacter(), 4, 70); // comparison value 280
        return attackResult;
    }

    public int enemyAction(){
        // same return as Battle.attack()
        int attackResult = 0;
        return attackResult;
    }

    public void endActivity(int result, String name, int victories){
        Intent intent = new Intent(BattleActivity.this, CharacterListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("result", result);
        bundle.putInt("victories", victories);
        bundle.putString("name", name);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
