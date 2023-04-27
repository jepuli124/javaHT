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

    public void doQuickAttack(){
        // can be called with onClick
        Battle.doQuickAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 200
        afterPlayerAttack();
    }

    public void doMediumAttack(){
        // can be called with onClick
        Battle.doMediumAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 240
        afterPlayerAttack();
    }

    public void doHeavyAttack(){
        // can be called with onClick
        Battle.doHeavyAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 280
        afterPlayerAttack();
    }

    private void afterPlayerAttack(){
        // should be called WHEN and ONLY when player has just made their move
        int battleStatusPreAi = battle.checkIfBattleEnded();
        if (battleStatusPreAi == 1 ||battleStatusPreAi == 2 || battleStatusPreAi == 3) {
            battle.endBattle(battleStatusPreAi, this);
            endActivity(battleStatusPreAi, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
        }

        if (battleStatusPreAi == 0) {
            battle.doAiAction();
            int battleStatusPostAi = battle.checkIfBattleEnded();
            if (battleStatusPostAi == 1 ||battleStatusPostAi == 2 || battleStatusPostAi == 3) {
                battle.endBattle(battleStatusPostAi, this);
                endActivity(battleStatusPostAi, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
            }
        }
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
