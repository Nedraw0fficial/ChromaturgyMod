package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ChromaturgyItems {

    public static final DeferredRegister.Items DYES =
            DeferredRegister.createItems(Chromaturgy.MODID);

    private static final Map<String, DeferredHolder<Item, Item>> DYE_ITEMS = new LinkedHashMap<>();

    static {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            String registryName = color.id() + "_dye";
            DeferredHolder<Item, Item> holder = DYES.registerSimpleItem(
                    registryName,
                    p -> p
            );
            DYE_ITEMS.put(color.id(), holder);
        }
    }

    private ChromaturgyItems() {}

    public static DeferredHolder<Item, Item> getDye(String colorId) {
        DeferredHolder<Item, Item> holder = DYE_ITEMS.get(colorId);
        if (holder == null) {
            throw new IllegalArgumentException("No registered dye for color id: " + colorId);
        }
        return holder;
    }

    public static void register(IEventBus modBus) {
        DYES.register(modBus);
    }
}