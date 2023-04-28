package com.example.javaht;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    private static final long serialVersionUID = 2398L;
    private String name;
    private String slotType; // what kind of slot this item can be equipped into. See ItemSlot.java for more info
    private ArrayList<StatChange> effects;

    private int image;

    public Item(String name, String slotType, ArrayList<StatChange> effects, int image) {
        this.name = name;
        this.slotType = slotType;
        this.effects = effects;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public String getSlotType() {
        return slotType;
    }

    public ArrayList<StatChange> getEffects() {
        return effects;
    }
}
