package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyWoolBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;

public final class WoolColorMatch {

    private static Map<Item, Item> DYE_TO_WOOL;

    private WoolColorMatch() {}

    private static Map<Item, Item> map() {
        if (DYE_TO_WOOL == null) {
            Map<Item, Item> map = new LinkedHashMap<>();
            map.put(Items.WHITE_DYE, Items.WHITE_WOOL);
            map.put(Items.ORANGE_DYE, Items.ORANGE_WOOL);
            map.put(Items.MAGENTA_DYE, Items.MAGENTA_WOOL);
            map.put(Items.LIGHT_BLUE_DYE, Items.LIGHT_BLUE_WOOL);
            map.put(Items.YELLOW_DYE, Items.YELLOW_WOOL);
            map.put(Items.LIME_DYE, Items.LIME_WOOL);
            map.put(Items.PINK_DYE, Items.PINK_WOOL);
            map.put(Items.GRAY_DYE, Items.GRAY_WOOL);
            map.put(Items.LIGHT_GRAY_DYE, Items.LIGHT_GRAY_WOOL);
            map.put(Items.CYAN_DYE, Items.CYAN_WOOL);
            map.put(Items.PURPLE_DYE, Items.PURPLE_WOOL);
            map.put(Items.BLUE_DYE, Items.BLUE_WOOL);
            map.put(Items.BROWN_DYE, Items.BROWN_WOOL);
            map.put(Items.GREEN_DYE, Items.GREEN_WOOL);
            map.put(Items.RED_DYE, Items.RED_WOOL);
            map.put(Items.BLACK_DYE, Items.BLACK_WOOL);
            for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
                map.put(com.nedraw.chromaturgy.registry.ChromaturgyItems.getDye(color.id()).get(),
                        ChromaturgyWoolBlocks.getWool(color.id()).get().asItem());
            }
            DYE_TO_WOOL = map;
        }
        return DYE_TO_WOOL;
    }

    public static Item woolForDye(Item dyeItem) {
        return map().getOrDefault(dyeItem, Items.WHITE_WOOL);
    }
}