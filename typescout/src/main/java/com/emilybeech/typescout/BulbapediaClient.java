package com.emilybeech.typescout;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BulbapediaClient {

    private static final String API =
            "https://bulbapedia.bulbagarden.net/w/api.php";

    private static final Pattern BASE_TYPE1 =
            Pattern.compile("\\|\\s*type1\\s*=\\s*([A-Za-z]+)");
    private static final Pattern BASE_TYPE2 =
            Pattern.compile("\\|\\s*type2\\s*=\\s*([A-Za-z]+)");

    private static final Pattern FORM_NAME =
            Pattern.compile("\\|\\s*form(\\d+)\\s*=\\s*([^\\n\\r\\|]+)");
    private static final Pattern FORM_TYPE1 =
            Pattern.compile("\\|\\s*form(\\d+)type1\\s*=\\s*([A-Za-z]+)");
    private static final Pattern FORM_TYPE2 =
            Pattern.compile("\\|\\s*form(\\d+)type2\\s*=\\s*([A-Za-z]+)");

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public PokemonEntry fetchPokemonEntry(String name)
            throws IOException, InterruptedException {

        String title = URLEncoder.encode(
                capitalize(name) + " (Pokémon)",
                StandardCharsets.UTF_8
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API +
                        "?action=parse&format=json&prop=wikitext&page=" + title))
                .header("User-Agent", "TypeScout")
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject root =
                JsonParser.parseString(response.body()).getAsJsonObject();

        JsonObject wikitext =
                root.getAsJsonObject("parse")
                    .getAsJsonObject("wikitext");

        String text = wikitext.get("*").getAsString();

        List<PokemonForm> forms = extractForms(name, text);
        return new PokemonEntry(capitalize(name), forms);
    }

    private List<PokemonForm> extractForms(String name, String text) {
        List<PokemonForm> forms = new ArrayList<>();

        Type baseType1 = Type.fromString(find(BASE_TYPE1, text));
        Type baseType2 = Type.fromString(findOptional(BASE_TYPE2, text));

        if (baseType2 == baseType1) {
            baseType2 = null;
        }
        
        forms.add(new PokemonForm(
                capitalize(name),
                baseType1,
                baseType2
        ));

        Matcher nameMatcher = FORM_NAME.matcher(text);
        List<Integer> indices = new ArrayList<>();

        while (nameMatcher.find()) {
            indices.add(Integer.parseInt(nameMatcher.group(1)));
        }

        indices.stream().distinct().sorted(Comparator.naturalOrder())
                .forEach(i -> {
                    String formName = findFormName(i, text);
                    Type t1 = Type.fromString(findFormType1(i, text));
                    Type t2 = Type.fromString(findFormType2(i, text));

                    if (t1 != null) {
                        forms.add(new PokemonForm(
                                capitalize(name) + " " + formName,
                                t1,
                                t2
                        ));
                    }
                });

        return forms;
    }

    private String findFormName(int i, String text) {
        Pattern p = Pattern.compile("\\|\\s*form" + i + "\\s*=\\s*([^\\n\\r\\|]+)");
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1).trim() : "Form " + i;
    }

    private String findFormType1(int i, String text) {
        Pattern p = Pattern.compile("\\|\\s*form" + i + "type1\\s*=\\s*([A-Za-z]+)");
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1) : null;
    }

    private String findFormType2(int i, String text) {
        Pattern p = Pattern.compile("\\|\\s*form" + i + "type2\\s*=\\s*([A-Za-z]+)");
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1) : null;
    }

    private String find(Pattern p, String text) {
        Matcher m = p.matcher(text);
        if (!m.find()) throw new RuntimeException("Type not found");
        return m.group(1);
    }

    private String findOptional(Pattern p, String text) {
        Matcher m = p.matcher(text);
        return m.find() ? m.group(1) : null;
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}

