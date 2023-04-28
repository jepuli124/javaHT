package com.example.javaht;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BattleActivity extends AppCompatActivity {

    private Battle battle;
    private Animation animation;
    private ImageView playerImg, enemyImg;
    //TextView battleTextView = (TextView) findViewById(R.id.textViewBatlefield);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_page);

        playerImg = findViewById(R.id.imageViewPlayer);
        enemyImg = findViewById(R.id.imageViewEnemy);

        if (CharacterStorage.getInstance().getEnemyFighter() == null) {
            battle = new Battle(CharacterStorage.getInstance().getMainFighter());
        } else {
            battle = new Battle(CharacterStorage.getInstance().getMainFighter(), CharacterStorage.getInstance().getEnemyFighter());
        }
        //battleTextView.setText(battle.getBattleText());
    }

    public void doQuickAttack(View view){
        // can be called with onClick
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.quick_player);
        playerImg.startAnimation(animation);
        battle.doQuickAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 200
        afterPlayerAttack();
    }

    public void doMediumAttack(View view){
        // can be called with onClick
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.medium_player);
        playerImg.startAnimation(animation);
        //battle.doMediumAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 240
        //afterPlayerAttack();
    }

    public void doHeavyAttack(View view){
        // can be called with onClick
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heavy_player);
        playerImg.startAnimation(animation);
        //battle.doHeavyAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 280
        //afterPlayerAttack();
    }

    private void afterPlayerAttack(){
        // should be called WHEN and ONLY when player has just made their move
        int battleStatusPreAi = battle.checkIfBattleEnded();
        if (battleStatusPreAi == 1 ||battleStatusPreAi == 2 || battleStatusPreAi == 3) {
            battle.endBattle(battleStatusPreAi, this);
            //battleTextView.setText(battle.getBattleText());
            endActivity(battleStatusPreAi, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
        }
        //battleTextView.setText(battle.getBattleText());
        if (battleStatusPreAi == 0) {
            battle.doAiAction(battle.getEnemyCharacter(), battle.getPlayerCharacter());
            int battleStatusPostAi = battle.checkIfBattleEnded();
            if (battleStatusPostAi == 1 ||battleStatusPostAi == 2 || battleStatusPostAi == 3) {
                battle.endBattle(battleStatusPostAi, this);
                //battleTextView.setText(battle.getBattleText());
                endActivity(battleStatusPostAi, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
            }
            //battleTextView.setText(battle.getBattleText());
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
