package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ColorDefinitions {

    private static final String RESOURCE_PATH =
            "/data/chromaturgy/chromaturgy_dyes/colors.json";

    private static List<ChromaturgyDyeColor> COLORS = Collections.emptyList();

    private ColorDefinitions() {}

    public static void load() {
        List<ChromaturgyDyeColor> loaded = new ArrayList<>();
        try (InputStream stream = ColorDefinitions.class.getResourceAsStream(RESOURCE_PATH)) {
            if (stream == null) {
                throw new IllegalStateException("Missing " + RESOURCE_PATH + " on classpath");
            }
            JsonArray array = JsonParser.parseReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8)
            ).getAsJsonArray();

            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                loaded.add(ChromaturgyDyeColor.of(
                        obj.get("id").getAsString(),
                        obj.get("hex").getAsString(),
                        obj.get("translation_key").getAsString(),
                        obj.get("custom_texture").getAsBoolean()
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Chromaturgy color definitions", e);
        }
        COLORS = List.copyOf(loaded);
    }

    public static List<ChromaturgyDyeColor> all() {
        if (COLORS.isEmpty()) {
            load();
        }
        return COLORS;
    }
}