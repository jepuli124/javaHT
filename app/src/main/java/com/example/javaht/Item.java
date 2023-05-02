package com.example.javaht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Item implements Serializable {
    private static final long serialVersionUID = 2398L;
    private static final int maxNumberOfEffects = 2;
    private String name;
    private String slotType; // what kind of slot this item can be equipped into. See ItemSlot.java for more info
    private ArrayList<StatChange> effects;

    private int image;

    // following lists are used in name generation
    private final static List<String> descriptiveWordsForNamesPreObject = Arrays.asList("Storm", "Mirror", "Alchemy", "Divine", "Anarchy", "Philosopher’s", "Changing", "Cursed", "Aethereal", "Blessed");
    private final static List<String> descriptiveWordsForNamesPostObject = Arrays.asList("Flame", "Hatred", "Angels", "Blinding", "Doom", "Loyalty", "Vengeance", "Silence", "Pain", "Transformation");
    private static final HashMap<String, List<String>> objectsForNames = new HashMap<>();
    static {
        objectsForNames.put("generic", Arrays.asList("Creator", "Stone", "Artifact", "Relic")); // these are used if key isn't found in HashMap
        objectsForNames.put("hand", Arrays.asList("Sword", "Blade", "Longsword", "Claymore"));
        objectsForNames.put("torso", Arrays.asList("Chestplate", "Armour", "Chain mail", "Robes"));
        objectsForNames.put("head", Arrays.asList("Helm", "Headguard", "Helmet", "Circlet"));
        objectsForNames.put("necklace", Arrays.asList("Necklace", "Pendant", "Chain", "Amulet"));
    }

    public Item(Character character) {
        // used for creation randomized items for given character
        this.slotType = generateSlotType(character);
        this.name = generateName(slotType);

        ArrayList<String> statNames = new ArrayList<>();
        for (Stat stat : character.getStats()) {
            statNames.add(stat.getName());
        }
        this.effects = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < Item.maxNumberOfEffects; i++) {
            StatChange effect= new StatChange(statNames.get(r.nextInt(statNames.size())), generateEffectStrength(character));
            if(!effect.IsInList(effects)){
                this.effects.add(effect);
            } else {
                for(StatChange stat: effects){
                    if(stat.getName().equals(effect.getName())){
                        stat.addValue(generateEffectStrength(character));
                    }
                }
            }

        }
    }

    public Item(String name, String slotType, ArrayList<StatChange> effects, int image) {
        this.name = name;
        this.slotType = slotType;
        this.effects = effects;
        this.image = image;
    }

    private static String generateName(String slotType) {
        String result = "";
        Random r = new Random();
        boolean descriptivePartDone = false;
        if (r.nextInt(2) == 0) {
            result += descriptiveWordsForNamesPreObject.get(r.nextInt(descriptiveWordsForNamesPreObject.size())) + " ";
            descriptivePartDone = true;
        }
        if (objectsForNames.containsKey(slotType)) {
            result += objectsForNames.get(slotType).get(r.nextInt(objectsForNames.get(slotType).size()));
        } else {
            result += objectsForNames.get("generic").get(r.nextInt(objectsForNames.get("generic").size()));
        }
        if (!descriptivePartDone) {
            result += " of " + descriptiveWordsForNamesPostObject.get(r.nextInt(descriptiveWordsForNamesPostObject.size()));
            descriptivePartDone = true;
        }
        return result;
    }

    private static String generateSlotType(Character character) {
        Random r = new Random();
        return character.getItems().get(r.nextInt(character.getItems().size())).getSlotType();
    }

    private static int generateEffectStrength(Character character) {
        Random r = new Random();
        int signMultiplier = 1 - 2 * r.nextInt(2); // -1 or 1
        int levelCurve = (int) Math.round(Math.log(5 * character.getLevel() + 5) * 5 - 4);
        return signMultiplier * levelCurve + 1;
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
