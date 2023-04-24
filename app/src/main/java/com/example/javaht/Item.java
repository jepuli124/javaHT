package com.example.javaht;

import java.util.ArrayList;

public class Item {
    private String slotType; // what kind of slot this item can be equipped into. See ItemSlot.java for more info
    private ArrayList<StatChange> effects;

    public Item(String slotType, ArrayList<StatChange> effects) {
        this.slotType = slotType;
        this.effects = effects;
    }

    public String getSlotType() {
        return slotType;
    }

    public ArrayList<StatChange> getEffects() {
        return effects;
    }
}
