package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
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
    private static final int enemyLevelRandomness = 5; // enemy level ranges from playerLevel - (randomness / 2) to playerLevel + (randomness / 2)
    // for enemyLevelRandomness, rounding is always done down e. g. 2.5 -> 2
    private static final AttackingMove quickAttack = new AttackingMove( "Quick attack", 2, 100); // comparison value 200
    private static final AttackingMove mediumAttack = new AttackingMove( "Medium attack", 3, 80); // comparison value 240
    private static final AttackingMove heavyAttack = new AttackingMove( "Heavy attack", 4, 70); // comparison value 280
    private static final List<AttackingMove> attackingMoves = new ArrayList<AttackingMove>(List.of(Battle.quickAttack, Battle.mediumAttack, Battle.heavyAttack));
    // list is used by Ai for easy access

    public Battle(Character originalPlayerCharacter) {
        Random r = new Random();
        this.battleType = 0;
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.playerCharacter = new Character(originalPlayerCharacter);
        this.originalEnemyCharacter = new Character(originalPlayerCharacter.getLevel() + r.nextInt(Battle.enemyLevelRandomness) - ((int) Math.floor((float) enemyLevelRandomness / 2)));
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
        if (defense > 0) {
            return Math.round((
                    (float) attack)
                    * ((float) attackPower)
                    / ((float) (defense)
            ));
        } else {
            return Math.round((
                    (float) attack)
                    * ((float) attackPower)
            );
        }

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

    private static int doSpecificAttack(AttackingMove attackingMove, Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        // this should be used by ONLY the Ai and is meant to be private
        int attackResult = Battle.attack(attackingCharacter, defendingCharacter, attackingMove.getAttackPower(), attackingMove.getHitChance());
        return attackResult;
    }

    public int doAiAction(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        // currently doesn't support status moves and is most likely quite bad
        for (AttackingMove am : Battle.attackingMoves) {
            if (am.getHitChance() == 100
                    && Battle.calculateMinimumDamage(am.getAttackPower(), defendingCharacter.getStatByName("Defense").getLevel(),
                    attackingCharacter.getStatByName("Attack").getLevel()) >= defendingCharacter.getStatByName("Health").getLevel()) {
                return Battle.doSpecificAttack(am, attackingCharacter, defendingCharacter);
            }
        }
        int i, calculatedDamage, currentDamage;
        float highestKOLikelyHood = 0, highestDamageDealtPerAttack = 0, KOs, damageSum;
        AttackingMove highestKOAM = Battle.attackingMoves.get(0), highestDamageAM = Battle.attackingMoves.get(0);
        for (AttackingMove am : Battle.attackingMoves) {
            KOs = 0;
            damageSum = 0;
            calculatedDamage = Math.round(Battle.calculateNonRandomizedDamage(am.getAttackPower(), defendingCharacter.getStatByName("Defense").getLevel(), attackingCharacter.getStatByName("Attack").getLevel()));
            for (i = 0; i < Battle.damageRandomness; i++) {
                currentDamage = Math.round(calculatedDamage * (100 - ((float)Battle.damageRandomness / 2) + i + 1));
                damageSum += currentDamage;
                if (currentDamage >= defendingCharacter.getStatByName("Health").getLevel()) {
                    KOs++;
                }
            }
            damageSum = damageSum * am.getHitChance() / 100 / Battle.damageRandomness;
            if (damageSum > highestDamageDealtPerAttack) {
                highestDamageDealtPerAttack = damageSum;
                highestDamageAM = am;
            }
            KOs = KOs * am.getHitChance() / 100 / Battle.damageRandomness;
            if (KOs > highestKOLikelyHood) {
                highestKOLikelyHood = KOs;
                highestKOAM = am;
            }
        }
        if (highestDamageAM.equals(highestKOAM)) {
            return Battle.doSpecificAttack(highestDamageAM, attackingCharacter, defendingCharacter);
        } else if (highestDamageDealtPerAttack / defendingCharacter.getStatByName("Health").getLevel() < highestKOLikelyHood) {
            return Battle.doSpecificAttack(highestKOAM, attackingCharacter, defendingCharacter);
        } else {
            return Battle.doSpecificAttack(highestDamageAM, attackingCharacter, defendingCharacter);
        }
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
