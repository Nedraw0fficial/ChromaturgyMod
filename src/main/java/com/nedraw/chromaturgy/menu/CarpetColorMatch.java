package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyCarpetBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ChromaturgyWoolBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;

public final class CarpetColorMatch {

    private static Map<Item, Item> DYE_TO_CARPET;
    private static Map<Item, Item> WOOL_TO_CARPET;

    private CarpetColorMatch() {}

    private static void init() {
        if (DYE_TO_CARPET != null) return;
        Map<Item, Item> dyeMap = new LinkedHashMap<>();
        Map<Item, Item> woolMap = new LinkedHashMap<>();

        dyeMap.put(Items.WHITE_DYE, Items.WHITE_CARPET);           woolMap.put(Items.WHITE_WOOL, Items.WHITE_CARPET);
        dyeMap.put(Items.ORANGE_DYE, Items.ORANGE_CARPET);         woolMap.put(Items.ORANGE_WOOL, Items.ORANGE_CARPET);
        dyeMap.put(Items.MAGENTA_DYE, Items.MAGENTA_CARPET);       woolMap.put(Items.MAGENTA_WOOL, Items.MAGENTA_CARPET);
        dyeMap.put(Items.LIGHT_BLUE_DYE, Items.LIGHT_BLUE_CARPET); woolMap.put(Items.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_CARPET);
        dyeMap.put(Items.YELLOW_DYE, Items.YELLOW_CARPET);         woolMap.put(Items.YELLOW_WOOL, Items.YELLOW_CARPET);
        dyeMap.put(Items.LIME_DYE, Items.LIME_CARPET);             woolMap.put(Items.LIME_WOOL, Items.LIME_CARPET);
        dyeMap.put(Items.PINK_DYE, Items.PINK_CARPET);             woolMap.put(Items.PINK_WOOL, Items.PINK_CARPET);
        dyeMap.put(Items.GRAY_DYE, Items.GRAY_CARPET);             woolMap.put(Items.GRAY_WOOL, Items.GRAY_CARPET);
        dyeMap.put(Items.LIGHT_GRAY_DYE, Items.LIGHT_GRAY_CARPET); woolMap.put(Items.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_CARPET);
        dyeMap.put(Items.CYAN_DYE, Items.CYAN_CARPET);             woolMap.put(Items.CYAN_WOOL, Items.CYAN_CARPET);
        dyeMap.put(Items.PURPLE_DYE, Items.PURPLE_CARPET);         woolMap.put(Items.PURPLE_WOOL, Items.PURPLE_CARPET);
        dyeMap.put(Items.BLUE_DYE, Items.BLUE_CARPET);             woolMap.put(Items.BLUE_WOOL, Items.BLUE_CARPET);
        dyeMap.put(Items.BROWN_DYE, Items.BROWN_CARPET);           woolMap.put(Items.BROWN_WOOL, Items.BROWN_CARPET);
        dyeMap.put(Items.GREEN_DYE, Items.GREEN_CARPET);           woolMap.put(Items.GREEN_WOOL, Items.GREEN_CARPET);
        dyeMap.put(Items.RED_DYE, Items.RED_CARPET);               woolMap.put(Items.RED_WOOL, Items.RED_CARPET);
        dyeMap.put(Items.BLACK_DYE, Items.BLACK_CARPET);           woolMap.put(Items.BLACK_WOOL, Items.BLACK_CARPET);

        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            Item dye = ChromaturgyItems.getDye(color.id()).get();
            Item wool = ChromaturgyWoolBlocks.getWool(color.id()).get().asItem();
            Item carpet = ChromaturgyCarpetBlocks.getCarpet(color.id()).get().asItem();
            dyeMap.put(dye, carpet);
            woolMap.put(wool, carpet);
        }

        DYE_TO_CARPET = dyeMap;
        WOOL_TO_CARPET = woolMap;
    }

    public static Item carpetForDye(Item dyeItem) {
        init();
        return DYE_TO_CARPET.getOrDefault(dyeItem, Items.WHITE_CARPET);
    }

    public static Item carpetForWool(Item woolItem) {
        init();
        return WOOL_TO_CARPET.get(woolItem);
    }

    public static boolean isKnownWool(Item item) {
        init();
        return WOOL_TO_CARPET.containsKey(item);
    }
}