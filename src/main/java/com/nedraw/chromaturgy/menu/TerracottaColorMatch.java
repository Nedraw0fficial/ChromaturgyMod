package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ChromaturgyTerracottaBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;

public final class TerracottaColorMatch {

    private static Map<Item, Item> DYE_TO_TERRACOTTA;

    private TerracottaColorMatch() {}

    private static void init() {
        if (DYE_TO_TERRACOTTA != null) return;
        Map<Item, Item> map = new LinkedHashMap<>();

        map.put(Items.WHITE_DYE, Items.WHITE_TERRACOTTA);
        map.put(Items.ORANGE_DYE, Items.ORANGE_TERRACOTTA);
        map.put(Items.MAGENTA_DYE, Items.MAGENTA_TERRACOTTA);
        map.put(Items.LIGHT_BLUE_DYE, Items.LIGHT_BLUE_TERRACOTTA);
        map.put(Items.YELLOW_DYE, Items.YELLOW_TERRACOTTA);
        map.put(Items.LIME_DYE, Items.LIME_TERRACOTTA);
        map.put(Items.PINK_DYE, Items.PINK_TERRACOTTA);
        map.put(Items.GRAY_DYE, Items.GRAY_TERRACOTTA);
        map.put(Items.LIGHT_GRAY_DYE, Items.LIGHT_GRAY_TERRACOTTA);
        map.put(Items.CYAN_DYE, Items.CYAN_TERRACOTTA);
        map.put(Items.PURPLE_DYE, Items.PURPLE_TERRACOTTA);
        map.put(Items.BLUE_DYE, Items.BLUE_TERRACOTTA);
        map.put(Items.BROWN_DYE, Items.BROWN_TERRACOTTA);
        map.put(Items.GREEN_DYE, Items.GREEN_TERRACOTTA);
        map.put(Items.RED_DYE, Items.RED_TERRACOTTA);
        map.put(Items.BLACK_DYE, Items.BLACK_TERRACOTTA);

        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            map.put(ChromaturgyItems.getDye(color.id()).get(),
                    ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get().asItem());
        }

        DYE_TO_TERRACOTTA = map;
    }

    public static Item terracottaForDye(Item dyeItem) {
        init();
        return DYE_TO_TERRACOTTA.getOrDefault(dyeItem, Items.WHITE_TERRACOTTA);
    }
}