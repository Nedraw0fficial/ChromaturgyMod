package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Chromaturgy.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CHROMATURGY_TAB =
            CREATIVE_MODE_TABS.register("chromaturgy_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.chromaturgy"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ChromaturgyItems.getDye("vermilion").get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ChromaturgyBlocks.PIGMENT_STATION_ITEM.get());
                        for (var color : ColorDefinitions.all()) {
                            output.accept(ChromaturgyItems.getDye(color.id()).get());
                        }
                    })
                    .build());

    private ChromaturgyCreativeTabs() {}

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}