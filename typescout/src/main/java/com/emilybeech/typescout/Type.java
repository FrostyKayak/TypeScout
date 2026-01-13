package com.emilybeech.typescout;

public enum Type {
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE,
    FIGHTING, POISON, GROUND, FLYING,
    PSYCHIC, BUG, ROCK, GHOST,
    DRAGON, DARK, STEEL, FAIRY;

    public static Type fromString(String value) {
        if (value == null) return null;
        return Type.valueOf(value.toUpperCase());
    }
}
