package com.nedraw.chromaturgy;

import com.nedraw.chromaturgy.datagen.*;
import com.nedraw.chromaturgy.menu.PigmentStationScreen;
import com.nedraw.chromaturgy.registry.*;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import com.nedraw.chromaturgy.menu.ChromaturgyMenuTypes;
import com.nedraw.chromaturgy.recipe.ChromaturgyRecipeSerializers;
import net.neoforged.neoforge.client.event.ScreenEvent;

import java.util.List;
import java.util.Set;

@Mod(Chromaturgy.MODID)
public class Chromaturgy {

    public static final String MODID = "chromaturgy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Chromaturgy(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ColorDefinitions.load();
        ChromaturgyItems.register(modEventBus);
        ChromaturgyBlocks.register(modEventBus);
        ChromaturgyWoolBlocks.register(modEventBus);
        ChromaturgyCarpetBlocks.register(modEventBus);
        ChromaturgyTerracottaBlocks.register(modEventBus);
        ChromaturgyCreativeTabs.register(modEventBus);
        ChromaturgyMenuTypes.register(modEventBus);
        ChromaturgyRecipeSerializers.register(modEventBus);

        modEventBus.addListener((RegisterMenuScreensEvent event) ->
                event.register(ChromaturgyMenuTypes.PIGMENT_STATION.get(), PigmentStationScreen::new));

        modEventBus.addListener(ChromaturgyBlockColors::register);
        NeoForge.EVENT_BUS.addListener(ChromaturgySectionOverlay::onItemTooltip);
        NeoForge.EVENT_BUS.addListener(ChromaturgySectionOverlay::onRenderBackground);

        modEventBus.addListener((GatherDataEvent.Client event) -> {
            event.createProvider(ChromaturgyModelProvider::new);
            event.createProvider((PackOutput output) ->
                    new ChromaturgyItemTagsProvider(output, event.getLookupProvider()));
            event.createProvider((PackOutput output) ->
                    new LootTableProvider(output, Set.of(),
                            List.of(new LootTableProvider.SubProviderEntry(
                                    ChromaturgyBlockLootProvider::new,
                                    LootContextParamSets.BLOCK)),
                            event.getLookupProvider()));
            event.createProvider((PackOutput output) ->
                    new ChromaturgyBlockTagsProvider(output, event.getLookupProvider()));
        });
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Chromaturgy is loading... (hello from Nedraw :D)");
    }
}