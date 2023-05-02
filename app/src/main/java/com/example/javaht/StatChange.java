package com.example.javaht;

import java.io.Serializable;
import java.util.ArrayList;

public class StatChange implements Serializable {
    private static final long serialVersionUID = 2398L;
    private String statName;
    private int value;

    public StatChange(String name, int value) {
        this.statName = name;
        this.value = value;
    }

    public String getName() {
        return statName;
    }

    public int getValue() {
        return value;
    }

    public void addValue(int value){
        this.value += value;
    }

    public boolean IsInList(ArrayList<StatChange> effects) {
        for (StatChange effect : effects) {
            if (effect.getName().equals(statName)) {
                return true;
            }
        }
        return false;
    }
}
