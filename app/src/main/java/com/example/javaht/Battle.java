package com.example.javaht;

import android.content.Context;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private int battleType; // 0 = against randomly generated enemy, 1 = against one of your own characters
    private int gotItem; // 0 = player did NOT receive item; 1 = player DID receive item
    private String battleText;
    private Character playerCharacter, enemyCharacter;
    private Character originalPlayerCharacter, originalEnemyCharacter; // these are the original objects and should NOT be edited after setting them in a constructor
    // original characters are kept in case there are stat changes during the battle
    // so those changes can be applied to the non-original copies without worry
    // and there is no need for dedicated hp counters
    private static final int damageRandomness = 20; // damage ranges from 100% - (randomness / 2)% to 100% + (randomness / 2)%
    private static final int enemyLevelRandomness = 5; // enemy level ranges from playerLevel - (randomness / 2) to playerLevel + (randomness / 2)
    private static final int chanceToGetItem = 33; // percent of times player gets item after victory
    // for enemyLevelRandomness, rounding is always done down e. g. 2.5 -> 2
    private static final AttackingMove quickAttack = new AttackingMove( "Quick attack", 20, 100);
    private static final AttackingMove mediumAttack = new AttackingMove( "Medium attack", 30, 80);
    private static final AttackingMove heavyAttack = new AttackingMove( "Heavy attack", 40, 70);
    private static final List<AttackingMove> attackingMoves = new ArrayList<AttackingMove>(List.of(Battle.quickAttack, Battle.mediumAttack, Battle.heavyAttack));
    // list is used by Ai for easy access

    public Battle(Character originalPlayerCharacter) throws IOException, ClassNotFoundException {
        // used when fighting against randomly generated enemy
        Random r = new Random();
        this.battleType = 0;
        this.gotItem = 0;
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.playerCharacter = cloning(originalPlayerCharacter);
        this.originalEnemyCharacter = new Character(originalPlayerCharacter.getBattlesWon() * 2 + 1 + r.nextInt(Battle.enemyLevelRandomness) - ((int) Math.floor((float) enemyLevelRandomness / 2)) + 2);
        this.enemyCharacter = cloning(originalEnemyCharacter);
        this.battleText = (originalPlayerCharacter.getName() + " vastaan " + originalEnemyCharacter.getName() + "\n");
        playerCharacter.applyItems();
        enemyCharacter.applyItems();
    }

    public Battle(Character originalPlayerCharacter, Character originalEnemyCharacter) throws IOException, ClassNotFoundException {
        this.battleType = 1;
        this.gotItem = 0;
        this.originalPlayerCharacter = originalPlayerCharacter;
        this.originalEnemyCharacter = originalEnemyCharacter;
        this.enemyCharacter = cloning(originalEnemyCharacter);
        this.playerCharacter = cloning(originalPlayerCharacter);

        playerCharacter.applyItems();
        enemyCharacter.applyItems();
        this.battleText = (playerCharacter.getName() + " vastaan " + enemyCharacter.getName() + "\n");
    }

    private Character cloning(Character character) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream(); // Writing character down to clone it
        ObjectOutputStream out = new ObjectOutputStream(outPut);
        out.writeObject(character);
        ByteArrayInputStream inPut = new ByteArrayInputStream(outPut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(inPut);
        return (Character) in.readObject();
    }

    public int attack(Character attackingCharacter, Character defendingCharacter, int attackPower, int hitChance) {
        // -1 = missed
        // otherwise return damage dealt
        if (checkIfHit(hitChance)) {
            int damageDealt = calculateRandomizedDamage(attackingCharacter.getStatByName("Attack").getLevel(), defendingCharacter.getStatByName("Defense").getLevel(), attackPower);
            // do at least 1 damage
            if (damageDealt < 1) {
                damageDealt = 1;
            }
            defendingCharacter.getStatByName("Health").changeLevel(-damageDealt);
             this.battleText += attackingCharacter.getName() + " teki " + String.valueOf(damageDealt) + " vahinkoa\n";
             this.battleText += defendingCharacter.getName() + " on " + defendingCharacter.getStatByName("Health").getLevel() + "Hp\n";
            return damageDealt;
        } else {
            this.battleText += attackingCharacter.getName() + " ei osunut\n";
            return -1;
        }
    }

    private static boolean checkIfHit(int hitChance) {
        Random r = new Random();
        return (r.nextInt(100)+1 <= hitChance);
    }

    private static int calculateNonRandomizedDamage(int attack, int defense, int attackPower) {
        if (defense > 1) {
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
        return Math.round(calculatedDamage * (100 - ((float)Battle.damageRandomness / 2) + r.nextInt(Battle.damageRandomness)) /100);
    }

    private static int calculateRandomizedDamage(int attack, int defense, int attackPower) {
        return randomizeDamage(calculateNonRandomizedDamage(attack, defense, attackPower));
    }

    private static int calculateMinimumDamage(int attack, int defense, int attackPower) {
        // used in AI decision making
        Random r = new Random();
        return Math.round(calculateNonRandomizedDamage(attack, defense, attackPower) * (100 - ((float)Battle.damageRandomness / 2) + 0 + 1));
    }

    public int doQuickAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = this.attack(attackingCharacter, defendingCharacter, quickAttack.getAttackPower(), quickAttack.getHitChance());
        return attackResult;
    }

    public int doMediumAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = this.attack(attackingCharacter, defendingCharacter, mediumAttack.getAttackPower(), mediumAttack.getHitChance());
        return attackResult;
    }

    public int doHeavyAttack(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        int attackResult = this.attack(attackingCharacter, defendingCharacter, heavyAttack.getAttackPower(), heavyAttack.getHitChance());
        return attackResult;
    }

    private int doSpecificAttack(AttackingMove attackingMove, Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        // this should be used by ONLY the Ai and is meant to be private
        int attackResult = this.attack(attackingCharacter, defendingCharacter, attackingMove.getAttackPower(), attackingMove.getHitChance());
        return attackResult;
    }

    public int doAiAction(Character attackingCharacter, Character defendingCharacter) {
        // same return as Battle.attack()
        // basic idea is to prioritize moves that are likely to win
        // then compare the best of those chances to the highest expected damage
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
            return this.doSpecificAttack(highestDamageAM, attackingCharacter, defendingCharacter);
        } else if (highestDamageDealtPerAttack / defendingCharacter.getStatByName("Health").getLevel() < highestKOLikelyHood) {
            return this.doSpecificAttack(highestKOAM, attackingCharacter, defendingCharacter);
        } else {
            return this.doSpecificAttack(highestDamageAM, attackingCharacter, defendingCharacter);
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
        this.battleText = "Taistelu päättyi\n";
        originalPlayerCharacter.addBattlesFought();
        originalEnemyCharacter.addBattlesFought();
        if(win == 1){
            this.battleText += playerCharacter.getName() + " voitti\n";
            if (this.getBattleType() == 0) {
                // only gain experience if battle type is against generated enemy
                originalPlayerCharacter.addToBattlesWon();
                originalPlayerCharacter.changeXp(originalEnemyCharacter.getGainedXp(originalPlayerCharacter));
                Random r = new Random();
                if (Battle.chanceToGetItem >= r.nextInt(100) + 1) {
                    originalPlayerCharacter.addItem(new Item(originalPlayerCharacter));
                    this.gotItem = 1;
                }
            }
        } else if (win == 2) {
            this.battleText += playerCharacter.getName() + " hävisi\n";
            if (this.getBattleType() == 0) {
                // only kill character if battle type is against generated enemy
                CharacterStorage s = CharacterStorage.getInstance();
                s.killCharacter(s.getCharacters().indexOf(originalPlayerCharacter), context);
            }
        } else if (win == 3) {
            this.battleText += " molemmat kaatuivat\n";
            if (this.getBattleType() == 0) {
                // only kill character if battle type is against generated enemy
                CharacterStorage s = CharacterStorage.getInstance();
                s.killCharacter(s.getCharacters().indexOf(originalPlayerCharacter), context);
            }
        }
    }

    public int getBattleType() {
        return battleType;
    }

    public int getGotItem() {
        return this.gotItem;
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public Character getEnemyCharacter() {
        return enemyCharacter;
    }

    public static int getChanceToGetItem() {
        return chanceToGetItem;
    }

    public String getOriginalPlayerCharacterName() {
        return this.originalPlayerCharacter.getName();
    }

    public int getOriginalPlayerCharacterVictories() {
        return this.originalPlayerCharacter.getBattlesWon();
    }

    public String getBattleText() {
        return battleText;
    }

    public void setBattleText(String battleText) {
        this.battleText = battleText;
    }
}
