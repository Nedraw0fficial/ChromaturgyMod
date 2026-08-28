package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ColorLookup {

    private static Map<Item, Integer> ITEM_TO_HEX;

    private ColorLookup() {}

    private static Map<Item, Integer> map() {
        if (ITEM_TO_HEX == null) {
            Map<Item, Integer> map = new LinkedHashMap<>();

            // Vanilla
            map.put(Items.WHITE_DYE, 0xF9FFFE);
            map.put(Items.ORANGE_DYE, 0xF9801D);
            map.put(Items.MAGENTA_DYE, 0xC74EBD);
            map.put(Items.LIGHT_BLUE_DYE, 0x3AB3DA);
            map.put(Items.YELLOW_DYE, 0xFED83D);
            map.put(Items.LIME_DYE, 0x80C71F);
            map.put(Items.PINK_DYE, 0xF38BAA);
            map.put(Items.GRAY_DYE, 0x474F52);
            map.put(Items.LIGHT_GRAY_DYE, 0x9D9D97);
            map.put(Items.CYAN_DYE, 0x169C9C);
            map.put(Items.PURPLE_DYE, 0x8932B8);
            map.put(Items.BLUE_DYE, 0x3C44AA);
            map.put(Items.BROWN_DYE, 0x835432);
            map.put(Items.GREEN_DYE, 0x5E7C16);
            map.put(Items.RED_DYE, 0xB02E26);
            map.put(Items.BLACK_DYE, 0x1D1D21);

            for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
                map.put(ChromaturgyItems.getDye(color.id()).get(), color.hex());
            }

            ITEM_TO_HEX = map;
        }
        return ITEM_TO_HEX;
    }

    public static float[] rgbToHsl(int r, int g, int b) {
        float rf = r / 255f, gf = g / 255f, bf = b / 255f;
        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float l = (max + min) / 2f;
        float h, s;

        if (max == min) {
            h = 0;
            s = 0;
        } else {
            float d = max - min;
            s = l > 0.5f ? d / (2f - max - min) : d / (max + min);
            if (max == rf) h = (gf - bf) / d + (gf < bf ? 6f : 0f);
            else if (max == gf) h = (bf - rf) / d + 2f;
            else h = (rf - gf) / d + 4f;
            h *= 60f;
        }
        return new float[]{h, s, l};
    }

    public static int[] hslToRgb(float h, float s, float l) {
        if (s == 0) {
            int v = Math.round(l * 255f);
            return new int[]{v, v, v};
        }
        float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
        float p = 2 * l - q;
        float hk = h / 360f;
        return new int[]{
                Math.round(hueToRgb(p, q, hk + 1f / 3f) * 255f),
                Math.round(hueToRgb(p, q, hk) * 255f),
                Math.round(hueToRgb(p, q, hk - 1f / 3f) * 255f)
        };
    }

    private static float hueToRgb(float p, float q, float t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1f / 6f) return p + (q - p) * 6f * t;
        if (t < 1f / 2f) return q;
        if (t < 2f / 3f) return p + (q - p) * (2f / 3f - t) * 6f;
        return p;
    }

    public static Item closestMatch(int r, int g, int b) {
        Item best = Items.WHITE_DYE;
        int bestDist = Integer.MAX_VALUE;
        for (Map.Entry<Item, Integer> entry : map().entrySet()) {
            int hex = entry.getValue();
            int dr = ((hex >> 16) & 0xFF) - r;
            int dg = ((hex >> 8) & 0xFF) - g;
            int db = (hex & 0xFF) - b;
            int dist = dr * dr + dg * dg + db * db;
            if (dist < bestDist) {
                bestDist = dist;
                best = entry.getKey();
            }
        }
        return best;
    }

    public static int hexOf(Item item) {
        return map().getOrDefault(item, 0xFFFFFF);
    }
}