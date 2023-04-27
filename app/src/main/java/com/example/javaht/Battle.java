package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

import java.util.Random;

public class Battle {
    private int battleType; // 0 = against randomly generated enemy, 1 = against one of your own characters
    private Character playerCharacter, enemyCharacter;
    private Character originalPlayerCharacter, originalEnemyCharacter; // these are the original objects and should NOT be edited after setting them in a constructor
    // original characters are kept in case there are stat changes during the battle
    // so those changes can be applied to the non-original copies without worry
    // and there is no need for dedicated hp counters

    public Battle(Character originalPlayerCharacter) {
        this.battleType = 0;
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.playerCharacter = new Character(originalPlayerCharacter);
        this.originalEnemyCharacter = new Character(originalPlayerCharacter.getLevel());
        this.enemyCharacter = new Character(originalEnemyCharacter);
        playerCharacter.applyItems();
        enemyCharacter.applyItems();
    }

    public Battle(Character originalPlayerCharacter, Character originalEnemyCharacter) {
        this.battleType = 1;
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.playerCharacter = new Character(originalPlayerCharacter);
        this.originalEnemyCharacter = originalEnemyCharacter;
        this.enemyCharacter = new Character(originalEnemyCharacter);
        playerCharacter.applyItems();
        enemyCharacter.applyItems();
    }

    public static int attack(Character attackingCharacter, Character defendingCharacter, int attackPower, int hitChance) {
        // -1 = missed
        // otherwise return damage dealt
        if (checkIfHit(hitChance)) {
            int damageDealt = calculateRandomizedDamage(attackingCharacter.getStatByName("Attack").getLevel(), defendingCharacter.getStatByName("Defense").getLevel(), attackPower);
            // do at least 1 damage
            if (damageDealt < 1) {
                damageDealt = 1;
            }
            defendingCharacter.getStatByName("Health").changeLevel(-damageDealt);
            return damageDealt;
        } else {
            return -1;
        }
    }

    private static boolean checkIfHit(int hitChance) {
        Random r = new Random();
        return (r.nextInt(100)+1 <= hitChance);
    }

    private static int calculateNonRandomizedDamage(int attack, int defense, int attackPower) {
        return Math.round((
                (float) attack)
                * ((float) attackPower)
                / ((float) (defense)
                ));
    }

    private static int randomizeDamage(int calculatedDamage) {
        final int randomness = 20;
        Random r = new Random();
        return Math.round(calculatedDamage * (100 - ((float)randomness / 2) + r.nextInt(randomness) + 1));
    }

    private static int calculateRandomizedDamage(int attack, int defense, int attackPower) {
        return randomizeDamage(calculateNonRandomizedDamage(attack, defense, attackPower));
    }

    public static int quickAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, 2, 100); // comparison value 200
        return attackResult;
    }

    public static int mediumAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, 3, 80); // comparison value 240
        return attackResult;
    }

    public static int heavyAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, 4, 70); // comparison value 280
        return attackResult;
    }

    public int checkIfBattleEnded(Character playerCharacter, Character enemyCharacter) {
        // 0 = battle did NOT end
        // 3 = tie
        // 2 = ENEMY won
        // 1 = PLAYER won
        //-1 = something went wrong

        int playerHP = playerCharacter.getStatByName("Health").getLevel();
        int enemyHP = enemyCharacter.getStatByName("Health").getLevel();
        if ((playerHP > 0) && (enemyHP > 0)) {
            return 0;
        } else if ((playerHP <= 0) && (enemyHP <= 0)) {
            return 3;
        } else if ((playerHP <= 0) && (enemyHP > 0)) {
            return 2;
        } else if ((playerHP > 0) && (enemyHP <= 0)) {
            return 1;
        }
        return -1;
    }

    public void endBattle(int win, Context context){
        if(win == 1){
            Toast.makeText(context, "Victory", Toast.LENGTH_SHORT).show();
            originalPlayerCharacter.addToBattlesWon();
            originalPlayerCharacter.changeXp(originalEnemyCharacter.getGainedXp(originalPlayerCharacter));
        } else if (win == 2) {
            Toast.makeText(context, "You perished", Toast.LENGTH_SHORT).show();
            if (this.getBattleType() == 0) {
                // only kill character if battle type is against generated enemy
                CharacterStorage s = CharacterStorage.getInstance();
                s.killCharacter(s.getCharacters().indexOf(originalPlayerCharacter));
            }
        } else if (win == 3) {
            Toast.makeText(context, "Both fighters perished", Toast.LENGTH_SHORT).show();
            if (this.getBattleType() == 0) {
                // only kill character if battle type is against generated enemy
                CharacterStorage s = CharacterStorage.getInstance();
                s.killCharacter(s.getCharacters().indexOf(originalPlayerCharacter));
            }
        }
        CharacterStorage.getInstance().setMainFighter(null);
        CharacterStorage.getInstance().setEnemyFighter(null);
    }

    public int getBattleType() {
        return battleType;
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public Character getEnemyCharacter() {
        return enemyCharacter;
    }
}
