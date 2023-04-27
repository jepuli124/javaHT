package com.example.javaht;

public class AttackingMove {
    private int attackPower, hitChance;

    public AttackingMove(int attackPower, int hitChance) {
        this.attackPower = attackPower;
        this.hitChance = hitChance;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHitChance() {
        return hitChance;
    }
}
