package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class Battle {
    private int battleType; // 0 = against randomly generated enemy, 1 = against one of your own characters
    private Character playerCharacter, enemyCharacter;
    private Character originalPlayerCharacter, originalEnemyCharacter; // these are the original objects and should NOT be edited after setting them in a constructor
    // original characters are kept in case there are stat changes during the battle
    // so those changes can be applied to the non-original copies without worry
    // and there is no need for dedicated hp counters
    private static final int damageRandomness = 20; // damage ranges from 100% - (randomness / 2)% to 100% + (randomness / 2)%
    private static final AttackingMove quickAttack = new AttackingMove( 2, 100); // comparison value 200
    private static final AttackingMove mediumAttack = new AttackingMove( 3, 80); // comparison value 240
    private static final AttackingMove heavyAttack = new AttackingMove( 4, 70); // comparison value 280

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
        Random r = new Random();
        return Math.round(calculatedDamage * (100 - ((float)Battle.damageRandomness / 2) + r.nextInt(Battle.damageRandomness) + 1));
    }

    private static int calculateRandomizedDamage(int attack, int defense, int attackPower) {
        return randomizeDamage(calculateNonRandomizedDamage(attack, defense, attackPower));
    }

    private static int calculateMinimumDamage(int attack, int defense, int attackPower) {
        // used in AI decision making
        Random r = new Random();
        return Math.round(calculateNonRandomizedDamage(attack, defense, attackPower) * (100 - ((float)Battle.damageRandomness / 2) + 0 + 1));
    }

    private static int calculateMaximumDamage(int attack, int defense, int attackPower) {
        // used in AI decision making
        Random r = new Random();
        return Math.round(calculateNonRandomizedDamage(attack, defense, attackPower) * (100 - ((float)Battle.damageRandomness / 2) + Battle.damageRandomness-1 + 1));
    }

    public static int doQuickAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, quickAttack.getAttackPower(), quickAttack.getHitChance());
        return attackResult;
    }

    public static int doMediumAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, mediumAttack.getAttackPower(), mediumAttack.getHitChance());
        return attackResult;
    }

    public static int doHeavyAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, heavyAttack.getAttackPower(), heavyAttack.getHitChance());
        return attackResult;
    }

    public void doAiAction() {
        ;// currently doesn't do anything

    }

    public int checkIfBattleEnded() {
        // 0 = battle did NOT end
        // 3 = tie
        // 2 = ENEMY won
        // 1 = PLAYER won
        //-1 = something went wrong

        int playerHP = this.playerCharacter.getStatByName("Health").getLevel();
        int enemyHP = this.enemyCharacter.getStatByName("Health").getLevel();
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
            if (this.getBattleType() == 0) {
                // only gain experience if battle type is against generated enemy
                originalPlayerCharacter.addToBattlesWon();
                originalPlayerCharacter.changeXp(originalEnemyCharacter.getGainedXp(originalPlayerCharacter));
            }
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

    public String getOriginalPlayerCharacterName() {
        return this.originalPlayerCharacter.getName();
    }

    public int getOriginalPlayerCharacterVictories() {
        return this.originalPlayerCharacter.getBattlesWon();
    }
}
