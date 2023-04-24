package com.example.javaht;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Character {
    private String name;
    private ArrayList<Stat> stats;
    private ArrayList<ItemSlot> itemLoadout;
    private int level;
    private int xp;
    private final static int startingLevel = 10;
    private final static List<ItemSlot> basicItemLoadout = Arrays.asList(new ItemSlot("hand"), new ItemSlot("torso"), new ItemSlot("head"), new ItemSlot("necklace"));

    public Character() {
        this.name = "";
        this.stats = new ArrayList<Stat>();
        this.itemLoadout = new ArrayList<ItemSlot>();
        for (int i = 0; i < basicItemLoadout.size(); i++) {
            this.itemLoadout.add(basicItemLoadout.get(i));
        }
        this.level = startingLevel;
        this.xp = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character(String name, ArrayList<Stat> stats, ArrayList<ItemSlot> itemLoadout, int level, int xp) {
        this.name = name;
        this.stats = new ArrayList<>(stats);
        this.itemLoadout = new ArrayList<>(itemLoadout);
        this.level = level;
        this.xp = xp;
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

    public ArrayList<ItemSlot> getItemLoadout() {
        return itemLoadout;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
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
        for (ItemSlot itemSlot : itemLoadout) {
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
