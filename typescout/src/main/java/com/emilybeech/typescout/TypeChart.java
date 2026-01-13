package com.emilybeech.typescout;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TypeChart {

    private final Map<Type, Map<Type, Double>> chart;

    private TypeChart(Map<Type, Map<Type, Double>> chart) {
        this.chart = chart;
    }

    public static TypeChart standard() {
        Map<Type, Map<Type, Double>> m = new EnumMap<>(Type.class);

        for (Type atk : Type.values()) {
            m.put(atk, new EnumMap<>(Type.class));
            for (Type def : Type.values()) {
                m.get(atk).put(def, 1.0);
            }
        }

        m.get(Type.FIRE).put(Type.GRASS, 2.0);
        m.get(Type.WATER).put(Type.FIRE, 2.0);
        m.get(Type.GRASS).put(Type.WATER, 2.0);
        m.get(Type.ELECTRIC).put(Type.WATER, 2.0);
        m.get(Type.GROUND).put(Type.ELECTRIC, 2.0);

        m.get(Type.ELECTRIC).put(Type.GROUND, 0.0);

        return new TypeChart(m);
    }

    public TypeReport buildReportForDefender(Type t1, Type t2) {
        List<String> weak = new ArrayList<>();
        List<String> resist = new ArrayList<>();
        List<String> immune = new ArrayList<>();

        for (Type atk : Type.values()) {
            double m1 = chart.get(atk).get(t1);
            double m2 = t2 == null ? 1.0 : chart.get(atk).get(t2);
            double total = m1 * m2;

            if (total == 0.0) immune.add(atk.name());
            else if (total > 1.0) weak.add(atk.name());
            else if (total < 1.0) resist.add(atk.name());
        }

        return new TypeReport(weak, resist, immune);
    }

    public String offensiveStrengthsAsText(Type t1, Type t2) {
        StringBuilder out = new StringBuilder();

        out.append(strengthsForType(t1));
        if (t2 != null && t2 != t1) {
            out.append("\n").append(strengthsForType(t2));
        }

        return out.toString();
    }

    private String strengthsForType(Type atk) {
        List<String> strong = new ArrayList<>();
        for (Type def : Type.values()) {
            if (chart.get(atk).get(def) > 1.0) {
                strong.add(def.name());
            }
        }
        return atk.name() + " strong against " + strong;
    }
}
