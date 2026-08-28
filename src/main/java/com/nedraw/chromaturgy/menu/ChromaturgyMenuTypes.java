package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, Chromaturgy.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<PigmentStationMenu>> PIGMENT_STATION =
            MENU_TYPES.register("pigment_station",
                    () -> new MenuType<>(PigmentStationMenu::new, null));

    private ChromaturgyMenuTypes() {}

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}