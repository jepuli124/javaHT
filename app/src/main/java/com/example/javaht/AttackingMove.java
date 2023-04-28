package com.example.javaht;

public class AttackingMove {
    private String name;
    private int attackPower, hitChance;

    public AttackingMove(String name, int attackPower, int hitChance) {
        this.name = name;
        this.attackPower = attackPower;
        this.hitChance = hitChance;
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getHitChance() {
        return hitChance;
    }
}
