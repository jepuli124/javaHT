package com.example.javaht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Character implements Serializable {
    private static final long serialVersionUID = 2398L;
    private String name;
    private ArrayList<Stat> stats;
    private ArrayList<ItemSlot> items;
    private int level;
    private int xp;
    private int battlesWon;
    private final static int startingLevel = 10;
    private final static List<ItemSlot> basicItemLoadout = Arrays.asList(new ItemSlot("hand"), new ItemSlot("torso"), new ItemSlot("head"), new ItemSlot("necklace"));
    // the following list is used in generating enemy names
    private final static List<String> mtgCreatureTypes = Arrays.asList("Advisor", "Aetherborn", "Ally", "Angel", "Anteater", "Antelope", "Ape", "Archer", "Archon", "Artificer", "Assassin", "Assembly-Worker", "Atog", "Aurochs", "Avatar", "Badger", "Barbarian", "Basilisk", "Bat", "Bear", "Beast", "Beeble", "Berserker", "Bird", "Blinkmoth", "Boar", "Bringer", "Brushwagg", "Camarid", "Camel", "Caribou", "Carrier", "Cat", "Centaur", "Cephalid", "Chimera", "Citizen", "Cleric", "Cockatrice", "Construct", "Coward", "Crab", "Crocodile", "Cyclops", "Dauthi", "Demon", "Deserter", "Devil", "Dinosaur", "Djinn", "Dragon", "Drake", "Dreadnought", "Drone", "Druid", "Dryad", "Dwarf", "Efreet", "Elder", "Eldrazi", "Elemental", "Elephant", "Elf", "Elk", "Eye", "Faerie", "Ferret", "Fish", "Flagbearer", "Fox", "Frog", "Fungus", "Gargoyle", "Germ", "Giant", "Gnome", "Goat", "Goblin", "God", "Golem", "Gorgon", "Graveborn", "Gremlin", "Griffin", "Hag", "Harpy", "Hellion", "Hippo", "Hippogriff", "Hormarid", "Homunculus", "Horror", "Horse", "Hound", "Human", "Hydra", "Hyena", "Illusion", "Imp", "Incarnation", "Insect", "Jellyfish", "Juggernaut", "Kavu", "Kirin", "Kithkin", "Knight", "Kobold", "Kor", "Kraken", "Lamia", "Lammasu", "Leech", "Leviathan", "Lhurgoyf", "Licid", "Lizard", "Manticore", "Masticore", "Mercenary", "Merfolk", "Metathran", "Minion", "Minotaur", "Mole", "Monger", "Mongoose", "Monk", "Moonfolk", "Mutant", "Myr", "Mystic", "Naga", "Nautilus", "Nephilim", "Nightmare", "Nightstalker", "Ninja", "Noggle", "Nomad", "Nymph", "Octopus", "Ogre", "Ooze", "Orb", "Orc", "Orgg", "Ouphe", "Ox", "Oyster", "Pegasus", "Pentavite", "Pest", "Phelddagrif", "Phoenix", "Pincher", "Pirate", "Plant", "Praetor", "Prism", "Processor", "Rabbit", "Rat", "Rebel", "Reflection", "Rhino", "Rigger", "Rogue", "Sable", "Salamander", "Samurai", "Sand", "Saproling", "Satyr", "Scarecrow", "Scion", "Scorpion", "Scout", "Serf", "Serpent", "Shade", "Shaman", "Shapeshifter", "Sheep", "Siren", "Skeleton", "Slith", "Sliver", "Slug", "Snake", "Soldier", "Soltari", "Spawn", "Specter", "Spellshaper", "Sphinx", "Spider", "Spike", "Spirit", "Splinter", "Sponge", "Squid", "Squirrel", "Starfish", "Surrakar", "Survivor", "Tetravite", "Thalakos", "Thopter", "Thrull", "Treefolk", "Triskelavite", "Troll", "Turtle", "Unicorn", "Vampire", "Vedalken", "Viashino", "Volver", "Wall", "Warrior", "Weird", "Werewolf", "Whale", "Wizard", "Wolf", "Wolverine", "Wombat", "Worm", "Wraith", "Wurm", "Yeti", "Zombie", "Zubera");

    public Character(String name) {
        this.name = name;
        this.stats = new ArrayList<Stat>(getRandomizedStats(startingLevel));
        this.items = new ArrayList<ItemSlot>();
        for (int i = 0; i < basicItemLoadout.size(); i++) {
            this.items.add(basicItemLoadout.get(i));
        }
        this.level = startingLevel;
        this.xp = 0;
        this.battlesWon = 0;
    }

    public Character(int level) {
        // used to generate enemies
        Random r = new Random();
        int i = 0;
        while (i <= r.nextInt(2) + 2) {
            this.name += mtgCreatureTypes.get(r.nextInt(mtgCreatureTypes.size()));
            i++;
        }
        this.stats = new ArrayList<Stat>(getRandomizedStats(level));
        this.items = new ArrayList<ItemSlot>();
        for (i = 0; i < basicItemLoadout.size(); i++) {
            this.items.add(basicItemLoadout.get(i));
        }
        this.level = startingLevel;
        this.xp = 0;
        this.battlesWon = 0;
    }

    public Character(Character original) {
        // essentially copies given character
        this.name = original.getName();
        this.stats = new ArrayList<Stat>(original.getStats());
        this.items = new ArrayList<ItemSlot>(original.getItems());
        this.level = original.getLevel();
        this.xp = original.getXp();
        this.battlesWon = original.getBattlesWon();
    }

    public Character(String name, ArrayList<Stat> stats, ArrayList<ItemSlot> items, int level, int xp, int battlesWon) {
        this.name = name;
        this.stats = new ArrayList<>(stats);
        this.items = new ArrayList<>(items);
        this.level = level;
        this.xp = xp;
        this.battlesWon = battlesWon;
    }

    public void setName(String name) {
        this.name = name;
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

    private static ArrayList<Stat> getRandomizedStats (int level) {
        Random r = new Random();
        final int statsInitialized = 3;
        float pointsAdded = statsInitialized;
        ArrayList<Stat> stats = new ArrayList<Stat>();
        stats.add(new Stat("Health", 1));
        stats.add(new Stat("Attack", 1));
        stats.add(new Stat("Defense", 1));
        int hpOdds;
        int atkOdds;
        int defOdds;
        int randInt;
        while (pointsAdded < level+statsInitialized) {
            hpOdds = Math.round(pointsAdded / stats.get(0).getLevel());
            atkOdds = Math.round(pointsAdded / stats.get(1).getLevel());
            defOdds = Math.round(pointsAdded / stats.get(2).getLevel());
            randInt = r.nextInt(Math.round(pointsAdded)) + 1;
            if (randInt <= hpOdds) {
                stats.get(0).changeLevel(1);
            } else if (randInt <= atkOdds + hpOdds) {
                stats.get(0).changeLevel(1);
            } else if (randInt <= defOdds + atkOdds + hpOdds) {
                stats.get(0).changeLevel(1);
            }
            pointsAdded++;
        }
        // double the hp stat and sometimes add 1
        stats.get(0).changeLevel(stats.get(0).getLevel() + r.nextInt(2));
        return stats;
    }

    public int getRequiredXp() {
        // you can change this formula to one that makes more sense
        return(this.getLevel() * 10);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Stat> getStats() {
        return stats;
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
