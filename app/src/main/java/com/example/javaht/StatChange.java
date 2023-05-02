package com.example.javaht;

import java.io.Serializable;

public class StatChange implements Serializable {
    private static final long serialVersionUID = 2398L;
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
