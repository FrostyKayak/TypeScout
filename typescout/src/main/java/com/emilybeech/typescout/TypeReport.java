package com.emilybeech.typescout;

import java.util.List;

public class TypeReport {

    private final List<String> weaknesses;
    private final List<String> resistances;
    private final List<String> immunities;

    public TypeReport(
            List<String> weaknesses,
            List<String> resistances,
            List<String> immunities
    ) {
        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.immunities = immunities;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public List<String> getResistances() {
        return resistances;
    }

    public List<String> getImmunities() {
        return immunities;
    }

    public String weaknessesAsText() {
        return weaknesses.isEmpty() ? "None" : weaknesses.toString();
    }

    public String resistancesAsText() {
        return resistances.isEmpty() ? "None" : resistances.toString();
    }

    public String immunitiesAsText() {
        return immunities.isEmpty() ? "None" : immunities.toString();
    }
}
