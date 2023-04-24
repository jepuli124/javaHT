package com.example.javaht;

public class Stat {
    private String name;
    private int level;

    public Stat(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void changeLevel(int amount) {
        level += amount;
    }
}
