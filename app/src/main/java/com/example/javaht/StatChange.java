package com.example.javaht;

public class StatChange {
    private String statName;
    private int value;

    public StatChange(String name, int value) {
        this.statName = statName;
        this.value = value;
    }

    public String getName() {
        return statName;
    }

    public int getValue() {
        return value;
    }
}
