package com.nedraw.chromaturgy;

import com.nedraw.chromaturgy.datagen.ChromaturgyModelProvider;
import net.minecraft.data.PackOutput;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ChromaturgyBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import com.nedraw.chromaturgy.registry.ChromaturgyCreativeTabs;
import com.nedraw.chromaturgy.datagen.ChromaturgyItemTagsProvider;

@Mod(Chromaturgy.MODID)
public class Chromaturgy {

    public static final String MODID = "chromaturgy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Chromaturgy(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ColorDefinitions.load();
        ChromaturgyItems.register(modEventBus);
        ChromaturgyBlocks.register(modEventBus);
        ChromaturgyCreativeTabs.register(modEventBus);

        modEventBus.addListener((GatherDataEvent.Client event) -> {
            event.createProvider(ChromaturgyModelProvider::new);
            event.createProvider((PackOutput output) ->
                    new ChromaturgyItemTagsProvider(output, event.getLookupProvider()));
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Chromaturgy is loading... (hello from Nedraw :D)");
    }
}