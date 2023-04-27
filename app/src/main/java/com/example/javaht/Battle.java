package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

public class Battle {
    private Character playerCharacter, enemyCharacter;
    private Character originalPlayerCharacter, originalEnemyCharacter; // these are the original objects and should NOT be edited after setting them in a constructor
    // original characters are kept in case there are stat changes during the battle
    // so those changes can be applied to the non-original copies without worry
    // and there is no need for dedicated hp counters

    public Battle(Character originalPlayerCharacter) {
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.playerCharacter = new Character(originalPlayerCharacter);
        this.originalEnemyCharacter = new Character(originalPlayerCharacter.getLevel());
        this.enemyCharacter = new Character(originalEnemyCharacter);
        playerCharacter.applyItems();
        enemyCharacter.applyItems();
    }

    public Battle(Character originalPlayerCharacter, Character originalEnemyCharacter) {
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.playerCharacter = new Character(originalPlayerCharacter);
        this.originalEnemyCharacter = originalEnemyCharacter;
        this.enemyCharacter = new Character(originalEnemyCharacter);
        playerCharacter.applyItems();
        enemyCharacter.applyItems();
    }

    public int attack(Character attackingCharacter, Character defendingCharacter) {
        // 0 = hit, but did NOT kill
        // 1 = hit and kill
        // 2 = missed
        // currently cannot miss

        int attackPower = 5; // future proofing in case will add different attacks
        int damageDealt = Math.round(((float)(attackingCharacter.getStatByName("Attack").getLevel())) * ((float)attackPower) / ((float)(defendingCharacter.getStatByName("Defense").getLevel())));
        if (damageDealt < 1) {
            damageDealt = 1;
        }
        defendingCharacter.getStatByName("Health").changeLevel(-damageDealt);
        if (defendingCharacter.getStatByName("Health").getLevel() > 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void endBattle(int win, Context context){
        if(win == 1){
            originalPlayerCharacter.addToBattlesWon();
        } else {
            CharacterStorage.getInstance().killCharacter(CharacterStorage.getInstance().getCharacters().indexOf(originalPlayerCharacter));
        }

        CharacterStorage.getInstance().setMainFighter(null);
        CharacterStorage.getInstance().setEnemyFighter(null);
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public Character getEnemyCharacter() {
        return enemyCharacter;
    }
}
