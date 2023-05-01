package com.example.javaht;

import android.content.Intent;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class BattleActivity extends AppCompatActivity {

    private Battle battle;
    private Animation animation;
    private ImageView playerImg, enemyImg;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_page);
        TextView battleTextView = findViewById(R.id.textViewBatlefield);
        TextView textViewMainCharacterName = findViewById(R.id.textViewPlayerName);
        TextView textViewMainCharacterLevel = findViewById(R.id.textViewPlayerLevel);
        TextView textViewEnemyName = findViewById(R.id.textViewEnemyName);
        TextView textViewEnemyLevel = findViewById(R.id.textViewEnemyLevel);
        playerImg = findViewById(R.id.imageViewPlayer);
        enemyImg = findViewById(R.id.imageViewEnemy);

        if (CharacterStorage.getInstance().getEnemyFighter() == null) {
            try {
                battle = new Battle(CharacterStorage.getInstance().getMainFighter());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                battle = new Battle(CharacterStorage.getInstance().getMainFighter(), CharacterStorage.getInstance().getEnemyFighter());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        playerImg.setImageResource(battle.getPlayerCharacter().getImageID());
        textViewMainCharacterName.setText(battle.getPlayerCharacter().getName());
        textViewMainCharacterLevel.setText(String.valueOf(battle.getPlayerCharacter().getLevel()));
        textViewEnemyName.setText(battle.getEnemyCharacter().getName());
        textViewEnemyLevel.setText(String.valueOf(battle.getEnemyCharacter().getLevel()));
        battleTextView.setText(battle.getBattleText());
        // check if one of the fighters died due to effects/items reducing their health
        int battleStatus = battle.checkIfBattleEnded();
        if (battleStatus == 1 ||battleStatus == 2 || battleStatus == 3) {
            battle.endBattle(battleStatus, this);
            battleTextView.setText(battle.getBattleText());
            endActivity(battleStatus, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
        }
    }

    public void doQuickAttack(View view){
        // can be called with onClick
        battle.setBattleText("");
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.quick_player);
        playerImg.startAnimation(animation);
        battle.doQuickAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 200
        afterPlayerAttack(1);
    }

    public void doMediumAttack(View view){
        // can be called with onClick
        battle.setBattleText("");
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.medium_player);
        playerImg.startAnimation(animation);
        battle.doMediumAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 240
        afterPlayerAttack(2);
    }

    public void doHeavyAttack(View view){
        // can be called with onClick
        battle.setBattleText("");
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heavy_player);
        playerImg.startAnimation(animation);
        battle.doHeavyAttack(battle.getPlayerCharacter(), battle.getEnemyCharacter()); // comparison value 280
        afterPlayerAttack(3);
    }

    private void afterPlayerAttack(int i){
        // should be called WHEN and ONLY when player has just made their move
        TextView battleTextView = findViewById(R.id.textViewBatlefield);
        int battleStatusPreAi = battle.checkIfBattleEnded();
        if (battleStatusPreAi == 1 ||battleStatusPreAi == 2 || battleStatusPreAi == 3) {
            battle.endBattle(battleStatusPreAi, this);
            battleTextView.setText(battle.getBattleText());
            endActivity(battleStatusPreAi, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
        }
        battleTextView.setText(battle.getBattleText());
        if (battleStatusPreAi == 0) {
            if (i == 1) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.quick_enemy);
                enemyImg.startAnimation(animation);
            } if (i == 2) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.medium_enemy);
                enemyImg.startAnimation(animation);
            } if (i == 3)   {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.heavy_enemy);
                enemyImg.startAnimation(animation);
            }

            battle.doAiAction(battle.getEnemyCharacter(), battle.getPlayerCharacter());
            int battleStatusPostAi = battle.checkIfBattleEnded();
            if (battleStatusPostAi == 1 ||battleStatusPostAi == 2 || battleStatusPostAi == 3) {
                battle.endBattle(battleStatusPostAi, this);
                battleTextView.setText(battle.getBattleText());
                endActivity(battleStatusPostAi, battle.getOriginalPlayerCharacterName(), battle.getOriginalPlayerCharacterVictories());
            }
            battleTextView.setText(battle.getBattleText());
        }
    }

    public void endActivity(int result, String name, int victories){
        CharacterStorage.getInstance().saveCharacters(this);
        Intent intent = new Intent(BattleActivity.this, AfterBattleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("result", result);
        bundle.putInt("victories", victories);
        bundle.putString("name", name);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
