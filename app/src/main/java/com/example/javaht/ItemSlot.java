package com.example.javaht;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemSlot implements Serializable {
    private static final long serialVersionUID = 2398L;
    private Item item;
    private final String slotType; // what type of item can be slotted into this slot (head, torso, hand...)

    public ItemSlot(String slotType) {
        this.slotType = slotType;
    }

    public Item getItem() {
        return item;
    }

    public String getSlots() {
        return slotType;
    }

    public boolean isEmpty() {
        if (item == null) {
            return(true);
        } else {
            return(false);
        }
    }

    public boolean isCompatible(Item itemToEquip) {
        // returns whether or not item can be equipped into this slot
        if (itemToEquip.getSlotType().matches(slotType)) {
            return(true);
        }
        return(false);
    }

    public int equip(Item itemToEquip) {
        //discards current equipment and equips given Item if able
        // 0 = equip successful
        // 1 = slots incompatible
        if (isCompatible(itemToEquip)) {
            item = itemToEquip;
            return(0);
        } else {
            return(1);
        }
    }
}
