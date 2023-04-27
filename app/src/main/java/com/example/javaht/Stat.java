package com.example.javaht;

import java.io.Serializable;

public class Stat implements Serializable {
    private static final long serialVersionUID = 2398L;
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
        if (level + amount < 1) {
            level = 0;
        } else {
            level += amount;
        }
    }
}
