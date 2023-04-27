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

    public void doQuickAttack(){
        // same return as Battle.attack()
        int attackResult = Battle.quickAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 200
    }

    public void doMediumAttack(){
        // same return as Battle.attack()
        int attackResult = Battle.mediumAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 240
    }

    public void doHeavyAttack(){
        // same return as Battle.attack()
        int attackResult = Battle.heavyAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 280
    }

    public void enemyAction(){
        // same return as Battle.attack()
        int attackResult = -1;
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
