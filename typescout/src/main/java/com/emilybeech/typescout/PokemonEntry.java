package com.emilybeech.typescout;

import java.util.List;

public class PokemonEntry {

    private final String name;
    private final List<PokemonForm> forms;

    public PokemonEntry(String name, List<PokemonForm> forms) {
        this.name = name;
        this.forms = forms;
    }

    public String getName() {
        return name;
    }

    public List<PokemonForm> getForms() {
        return forms;
    }
}
