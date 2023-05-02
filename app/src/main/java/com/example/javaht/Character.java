package com.example.javaht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Character implements Serializable {
    private static final long serialVersionUID = 2398L;
    private String name;
    private ArrayList<Stat> stats;
    private ArrayList<ItemSlot> items;
    private ArrayList<Item> itemStorage;
    private int level;
    private int xp;

    private int id;
    private int battlesFought = 0;
    private int battlesWon = 0;
    private int imageID = R.drawable.crusader_154623_1280;
    private final static int startingLevel = 1;
    private final static int additionalStartingStatPoints = 19;

    // statMultipliers has the multipliers for each stat that doesn't use 1
    // key is the name of the stat and value is the multiplier
    private static final HashMap<String, Integer> statMultipliers = new HashMap<String, Integer>();
    static {
        statMultipliers.put("Health", Integer.valueOf(10));
        statMultipliers.put("Defense", Integer.valueOf(2));
    }
    // statStartingValues has the points invested into each stat (if not specified, 1 will be used)
    // key is the name of the stat and value is the starting value
    private static final HashMap<String, Integer> statStartingValues = new HashMap<String, Integer>();
    static {
        statStartingValues.put("Health", Integer.valueOf(10));
        statStartingValues.put("Defense", Integer.valueOf(1));
    }
    private final static List<ItemSlot> basicItemLoadout = Arrays.asList(new ItemSlot("hand"), new ItemSlot("torso"), new ItemSlot("head"), new ItemSlot("necklace"));
    // the following list is used in generating enemy names
    private final static List<String> mtgCreatureTypes = Arrays.asList("Advisor", "Aetherborn", "Ally", "Angel", "Anteater", "Antelope", "Ape", "Archer", "Archon", "Artificer", "Assassin", "Assembly-Worker", "Atog", "Aurochs", "Avatar", "Badger", "Barbarian", "Basilisk", "Bat", "Bear", "Beast", "Beeble", "Berserker", "Bird", "Blinkmoth", "Boar", "Bringer", "Brushwagg", "Camarid", "Camel", "Caribou", "Carrier", "Cat", "Centaur", "Cephalid", "Chimera", "Citizen", "Cleric", "Cockatrice", "Construct", "Coward", "Crab", "Crocodile", "Cyclops", "Dauthi", "Demon", "Deserter", "Devil", "Dinosaur", "Djinn", "Dragon", "Drake", "Dreadnought", "Drone", "Druid", "Dryad", "Dwarf", "Efreet", "Elder", "Eldrazi", "Elemental", "Elephant", "Elf", "Elk", "Eye", "Faerie", "Ferret", "Fish", "Flagbearer", "Fox", "Frog", "Fungus", "Gargoyle", "Germ", "Giant", "Gnome", "Goat", "Goblin", "God", "Golem", "Gorgon", "Graveborn", "Gremlin", "Griffin", "Hag", "Harpy", "Hellion", "Hippo", "Hippogriff", "Hormarid", "Homunculus", "Horror", "Horse", "Hound", "Human", "Hydra", "Hyena", "Illusion", "Imp", "Incarnation", "Insect", "Jellyfish", "Juggernaut", "Kavu", "Kirin", "Kithkin", "Knight", "Kobold", "Kor", "Kraken", "Lamia", "Lammasu", "Leech", "Leviathan", "Lhurgoyf", "Licid", "Lizard", "Manticore", "Masticore", "Mercenary", "Merfolk", "Metathran", "Minion", "Minotaur", "Mole", "Monger", "Mongoose", "Monk", "Moonfolk", "Mutant", "Myr", "Mystic", "Naga", "Nautilus", "Nephilim", "Nightmare", "Nightstalker", "Ninja", "Noggle", "Nomad", "Nymph", "Octopus", "Ogre", "Ooze", "Orb", "Orc", "Orgg", "Ouphe", "Ox", "Oyster", "Pegasus", "Pentavite", "Pest", "Phelddagrif", "Phoenix", "Pincher", "Pirate", "Plant", "Praetor", "Prism", "Processor", "Rabbit", "Rat", "Rebel", "Reflection", "Rhino", "Rigger", "Rogue", "Sable", "Salamander", "Samurai", "Sand", "Saproling", "Satyr", "Scarecrow", "Scion", "Scorpion", "Scout", "Serf", "Serpent", "Shade", "Shaman", "Shapeshifter", "Sheep", "Siren", "Skeleton", "Slith", "Sliver", "Slug", "Snake", "Soldier", "Soltari", "Spawn", "Specter", "Spellshaper", "Sphinx", "Spider", "Spike", "Spirit", "Splinter", "Sponge", "Squid", "Squirrel", "Starfish", "Surrakar", "Survivor", "Tetravite", "Thalakos", "Thopter", "Thrull", "Treefolk", "Triskelavite", "Troll", "Turtle", "Unicorn", "Vampire", "Vedalken", "Viashino", "Volver", "Wall", "Warrior", "Weird", "Werewolf", "Whale", "Wizard", "Wolf", "Wolverine", "Wombat", "Worm", "Wraith", "Wurm", "Yeti", "Zombie", "Zubera");

    public Character(String name, int Health, int Attack, int Defense) { //Choose your starting stats
        this.name = name;
        ArrayList<String> statsToBeInitialized = new ArrayList<>();
        statsToBeInitialized.add("Health");
        statsToBeInitialized.add("Attack");
        statsToBeInitialized.add("Defense");

        int pointsInvested = 0;
        this.stats = new ArrayList<Stat>();
        for (String statName : statsToBeInitialized) {
            int initialStatLevel = (getStatMultiplier(statName) * getStatStartingInvestment(statName));
            this.stats.add(new Stat(statName, initialStatLevel)); //
            pointsInvested += getStatStartingInvestment(statName);
        }

        this.getStatByName("Health").changeLevel(Health * getStatMultiplier("Health"));
        this.getStatByName("Attack").changeLevel(Attack * getStatMultiplier("Attack"));
        this.getStatByName("Defense").changeLevel(Defense * getStatMultiplier("Defense"));
        pointsInvested += Health + Attack + Defense;

        while (pointsInvested <= startingLevel + additionalStartingStatPoints) {
            this.addToRandomStat();
            pointsInvested++;
        }

        this.items = new ArrayList<ItemSlot>();
        this.itemStorage = new ArrayList<Item>();
        for (int i = 0; i < basicItemLoadout.size(); i++) {
            this.items.add(basicItemLoadout.get(i));
        }
        this.level = startingLevel;
        this.xp = 0;
        this.battlesWon = 0;
        this.battlesFought = 0;
        this.id = CharacterStorage.getInstance().getCharacters().size();
    }

    public Character(int level) {
        // used to generate enemies
        Random r = new Random();
        int i = 0;
        this.name = mtgCreatureTypes.get(r.nextInt(mtgCreatureTypes.size()));
        while (i < r.nextInt(3)) {
            this.name += " " + mtgCreatureTypes.get(r.nextInt(mtgCreatureTypes.size()));
            i++;
        }

        ArrayList<String> statsToBeInitialized = new ArrayList<>();
        statsToBeInitialized.add("Health");
        statsToBeInitialized.add("Attack");
        statsToBeInitialized.add("Defense");

        int pointsInvested = 0;
        this.stats = new ArrayList<Stat>();
        for (String statName : statsToBeInitialized) {
            int initialStatLevel = (getStatMultiplier(statName) * getStatStartingInvestment(statName));
            this.stats.add(new Stat(statName, initialStatLevel)); //
            pointsInvested += getStatStartingInvestment(statName);
        }

        while (pointsInvested <= level + additionalStartingStatPoints) {
            this.addToRandomStat();
            pointsInvested++;
        }

        this.items = new ArrayList<ItemSlot>();
        for (i = 0; i < basicItemLoadout.size(); i++) {
            this.items.add(basicItemLoadout.get(i));
        }

        this.level = level;
        i = startingLevel;
        while ((i < level) && (r.nextInt(100) + 1 < Battle.getChanceToGetItem())) {
            this.setItem(new Item(this));
            i++;
        }

        this.xp = 0;
        this.battlesWon = 0;
        this.battlesFought = 0;
    }

    public Character(String name, ArrayList<Stat> stats, ArrayList<ItemSlot> items, int level, int xp, int battlesWon) {
        this.name = name;
        this.stats = new ArrayList<>(stats);
        this.items = new ArrayList<>(items);
        this.level = level;
        this.xp = xp;
        this.battlesWon = battlesWon;
        this.battlesFought = 0;
    }

    private static int getStatStartingInvestment(String statName) {
        if (Character.statStartingValues.containsKey(statName)) {
            return Character.statStartingValues.get(statName);
        } else {
            return 1;
        }
    }

    private static int getStatMultiplier(String statName) {
        if (Character.statMultipliers.containsKey(statName)) {
            return Character.statMultipliers.get(statName);
        } else {
            return 1;
        }
    }

    public static int getPointsToBeAssignedOnCreation() {
        int setInvestments = 0;
        for (Map.Entry<String, Integer> a : Character.statStartingValues.entrySet()) {
            setInvestments += a.getValue().intValue();
        }
        int value = startingLevel + additionalStartingStatPoints - setInvestments;
        if (value < 0) {
            return 0;
        }else {
            return value;
        }
    }

    public void setImage(int imageID) {
        this.imageID = imageID;
    }

    public Stat getStatByName(String name) {
        for (Stat stat : this.getStats()) {
            if (stat.getName().equals(name)) {
                return stat;
            }
        }
        // if stat isn't found, add and return such stat with level 0
        this.getStats().add(new Stat(name, 0));
        return this.getStatByName(name);
    }

    private void addToRandomStat() {
        // adds 1 point to a random stat with weighted odds towards all stats being similar levels
        ArrayList<Integer> statAmounts = new ArrayList<Integer>();
        int totalStats = 0;
        for (Stat stat : this.getStats()) {
            statAmounts.add(Math.round(((float) stat.getLevel()) / Character.getStatMultiplier(stat.getName())));
            totalStats += Math.round(((float) stat.getLevel()) / Character.getStatMultiplier(stat.getName()));
        }
        if (totalStats < 1) {
            totalStats = 1;
        }
        ArrayList<Integer> statWeights = new ArrayList<Integer>();
        for (Integer value : statAmounts) {
            if (value < 1) {
                value = 1;
            }
            statWeights.add((Integer.valueOf((int) Math.ceil(totalStats / value))));
        }
        ArrayList<Integer> statIndexes = new ArrayList<Integer>();
        int j = 0;
        for (Integer weight : statWeights) {
            for (int i = 0; i < weight; i++) {
                statIndexes.add(j);
            }
            j++;
        }
        Random r = new Random();
        int randInt = r.nextInt(statIndexes.size());
        Stat statTobeAddedTo = getStats().get(statIndexes.get(randInt));
        statTobeAddedTo.changeLevel(Character.getStatMultiplier(statTobeAddedTo.getName()));
    }


    public int getRequiredXp() {
        // you can change this formula to one that makes more sense
        return((this.getLevel() + 19)* 2);
    }

    public int getGainedXp(Character killer) {
        final int battleIncreasePerLevel = 2;
        int gainedXp = (int)Math.round((float)(this.getRequiredXp() + killer.getRequiredXp()) / (2 * battleIncreasePerLevel));
        return gainedXp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }

    public ArrayList<Item> getItemStorage() {
        return itemStorage;
    }

    public void addItem(Item item){
        itemStorage.add(item);
    }

    public void setItem(int id){
        for(ItemSlot itemSlot : items){
            if(itemSlot.isCompatible(itemStorage.get(id))){
                if(itemSlot.getItem() != null){
                    itemStorage.add(itemSlot.getItem());
                }
                itemSlot.equip(itemStorage.get(id));
            }
        }
    }

    public void setItem(Item itemToSet) {
        for(ItemSlot itemSlot : items){
            if(itemSlot.isCompatible(itemToSet)){
                itemSlot.equip(itemToSet);
            }
        }
    }

    public ArrayList<ItemSlot> getItems() {
        return items;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getBattlesWon() {
        return battlesWon;
    }

    public int getBattlesFought(){ return battlesFought; }
    public int getImageID() {return imageID; }

    public void addBattlesFought(){battlesFought++;}

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
        this.addToRandomStat();
    }

    public void addToBattlesWon() {
        battlesWon++;
    }

    public void addToBattlesWon(int amount) {
        if (battlesWon + amount < 1) {
            battlesWon = 0;
        } else {
            battlesWon += amount;
        }
    }

    public void changeStat(StatChange statChange) {
        this.getStatByName(statChange.getName()).changeLevel(statChange.getValue());
    }

    public void changeStat(ArrayList<StatChange> statChanges) {
        for (StatChange statChange :statChanges) {
            this.changeStat(statChange);
        }
    }

    public void changeStat(String name, int change) {
        getStatByName(name).changeLevel(change);
    }

    public void applyItems() {
        boolean foundStat = false;
        for (ItemSlot itemSlot : items) {
            if (itemSlot.getItem() != null) {
                this.changeStat(itemSlot.getItem().getEffects());
            }
        }
    }
}
