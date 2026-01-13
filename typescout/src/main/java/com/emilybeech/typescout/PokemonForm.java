package com.emilybeech.typescout;

public class PokemonForm {

    private final String name;
    private final Type type1;
    private final Type type2;

    public PokemonForm(String name, Type type1, Type type2) {
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getName() {
        return name;
    }

    public Type getType1() {
        return type1;
    }

    public Type getType2() {
        return type2;
    }

    public String typeSummary() {
        if (type2 == null) {
            return type1.name();
        }
        return type1.name() + " and " + type2.name();
    }
}
