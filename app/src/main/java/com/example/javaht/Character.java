package com.example.javaht;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Character {
    private String name;
    private ArrayList<Stat> stats;
    private ArrayList<ItemSlot> items;
    private int level;
    private int xp;
    private int battlesWon;
    private final static int startingLevel = 10;
    private final static List<ItemSlot> basicItemLoadout = Arrays.asList(new ItemSlot("hand"), new ItemSlot("torso"), new ItemSlot("head"), new ItemSlot("necklace"));

    public Character() {
        this.name = "";
        this.stats = new ArrayList<Stat>(getRandomizedStats(startingLevel));
        this.items = new ArrayList<ItemSlot>();
        for (int i = 0; i < basicItemLoadout.size(); i++) {
            this.items.add(basicItemLoadout.get(i));
        }
        this.level = startingLevel;
        this.xp = 0;
        this.battlesWon = 0;
    }

    public Character(String name, ArrayList<Stat> stats, ArrayList<ItemSlot> items, int level, int xp, int battlesWon) {
        this.name = name;
        this.stats = new ArrayList<>(stats);
        this.items = new ArrayList<>(items);
        this.level = level;
        this.xp = xp;
        this.battlesWon = battlesWon;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static ArrayList<Stat> getRandomizedStats (int level) {
        Random r = new Random();
        int hp = 1;
        int atk = 1;
        int def = 1;
        int hpOdds;
        int atkOdds;
        int defOdds;
        int randInt;
        while (hp + atk + def < level) {
            hpOdds = Math.round((hp + atk + def) / hp);
            atkOdds = Math.round((hp + atk + def) / atk);
            defOdds = Math.round((hp + atk + def) / def);
            randInt = r.nextInt(hp + atk + def) + 1;
            if (randInt <= hpOdds) {
                hp++;
            } else if (randInt <= atkOdds + hpOdds) {
                atk++;
            } else if (randInt <= defOdds + atkOdds + hpOdds) {
                def++;
            }
        }
        ArrayList<Stat> stats = new ArrayList<Stat>();
        stats.add(new Stat("Health", r.nextInt(2)+1+2*hp));
        stats.add(new Stat("Attack", 1+2*atk));
        stats.add(new Stat("Defense", 1+2*def));
        return stats;
    }

    public int getRequiredXp() {
        // you can change this formula to one that makes more sense
        return(this.getLevel() * 10);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public ArrayList<ItemSlot> getItems() {
        return items;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public int changeXp(int amount) {
        // 0 = all is ok, did NOT level up
        // 1 = all is ok, did level up
        xp += amount;
        int requiredXp = this.getRequiredXp();
        if (xp >= requiredXp) {
            this.levelUp();
            xp -= requiredXp;
            return 1;
        }
        return 0;
    }

    private void levelUp() {
        level++;
    }

    private void addToBattlesWon() {
        battlesWon++;
    }

    private void addToBattlesWon(int amount) {
        if (battlesWon + amount < 1) {
            battlesWon = 0;
        } else {
            battlesWon += amount;
        }
    }

    public void changeStat(StatChange statChange) {
        boolean foundStat = false;
        for (Stat stat : stats) {
            if (stat.getName().matches(statChange.getName())) {
                stat.changeLevel(statChange.getValue());
                foundStat = true;
                break;
            }
        }
        if (!foundStat) { //if corresponding stat is not found, create such stat
            stats.add(new Stat(statChange.getName(), statChange.getValue()));
        }
    }

    public void changeStat(ArrayList<StatChange> statChanges) {
        boolean foundStat = false;
        for (StatChange statChange :statChanges) {
            for (Stat stat : stats) {
                if (stat.getName().matches(statChange.getName())) {
                    stat.changeLevel(statChange.getValue());
                    foundStat = true;
                    break;
                }
            }
            if (!foundStat) { //if corresponding stat is not found, create such stat
                stats.add(new Stat(statChange.getName(), statChange.getValue()));
            }
            foundStat = false;
        }
    }

    public void changeStat(String name, int change) {
        boolean foundStat = false;
        for (Stat stat : stats) {
            if (stat.getName().matches(name)) {
                stat.changeLevel(change);
                foundStat = true;
                break;
            }
        }
        if (!foundStat) { //if corresponding stat is not found, create such stat
            stats.add(new Stat(name, change));
        }
    }

    public void applyItems() {
        boolean foundStat = false;
        for (ItemSlot itemSlot : items) {
            if (itemSlot.getItem() != null) {
                for (StatChange effect : itemSlot.getItem().getEffects()) {
                    for (Stat stat : stats) {
                        if (stat.getName().matches(effect.getName())) {
                            stat.changeLevel(effect.getValue());
                            foundStat = true;
                            break;
                        }
                    }
                    if (!foundStat) { //if corresponding stat is not found, create such stat
                        stats.add(new Stat(effect.getName(), effect.getValue()));
                    }
                    foundStat = false;
                }
            }
        }
    }
}
