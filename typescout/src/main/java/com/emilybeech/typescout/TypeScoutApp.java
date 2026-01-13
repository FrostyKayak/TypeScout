package com.emilybeech.typescout;

import java.util.List;
import java.util.Scanner;

public class TypeScoutApp {

    public static void main(String[] args) {
        System.out.println("TypeScout");
        System.out.println("Pokemon type matchup scout");
        System.out.println("Type quit to exit");

        BulbapediaClient client = new BulbapediaClient();
        TypeChart chart = TypeChart.standard();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nPokemon name: ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("quit")) {
                    System.out.println("Goodbye");
                    return;
                }

                if (input.isBlank()) {
                    continue;
                }

                try {
                    PokemonEntry entry = client.fetchPokemonEntry(input);

                    PokemonForm chosenForm = chooseForm(entry.getForms(), scanner);

                    System.out.println("\nUsing form: " + chosenForm.getName());
                    System.out.println("Types: " + chosenForm.typeSummary());

                    TypeReport report = chart.buildReportForDefender(
                            chosenForm.getType1(),
                            chosenForm.getType2()
                    );

                    System.out.println("\nDefensive weaknesses");
                    System.out.println(report.weaknessesAsText());

                    System.out.println("\nDefensive resistances");
                    System.out.println(report.resistancesAsText());

                    System.out.println("\nDefensive immunities");
                    System.out.println(report.immunitiesAsText());

                    System.out.println("\nOffensive strengths");
                    System.out.println(chart.offensiveStrengthsAsText(
                    		chosenForm.getType1(),
                    		chosenForm.getType2()
                    ));

                } catch (Exception e) {
                    System.out.println("Could not retrieve that Pokemon");
                }
            }
        }
    }

    private static PokemonForm chooseForm(List<PokemonForm> forms, Scanner scanner) {
        if (forms.size() == 1) {
            return forms.get(0);
        }

        System.out.println("\nAvailable forms");
        for (int i = 0; i < forms.size(); i++) {
            PokemonForm f = forms.get(i);
            System.out.println((i + 1) + ". " + f.getName() + " (" + f.typeSummary() + ")");
        }

        while (true) {
            System.out.print("Choose a form number: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= forms.size()) {
                    return forms.get(choice - 1);
                }
            } catch (NumberFormatException ignored) { }

            System.out.println("Invalid selection");
        }
    }
}
