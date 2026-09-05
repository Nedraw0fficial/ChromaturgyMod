package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.block.ChromaturgyPaintedBlockRegistry;
import com.nedraw.chromaturgy.menu.ColorLookup;
import com.nedraw.chromaturgy.registry.ChromaturgyCarpetBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyTerracottaBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyWoolBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.client.color.block.BlockTintSources;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

public final class ChromaturgyBlockColors {

    private ChromaturgyBlockColors() {}

    public static void register(RegisterColorHandlersEvent.BlockTintSources event) {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            event.register(
                    List.of(BlockTintSources.constant(0xFF000000 | color.hex())),
                    ChromaturgyWoolBlocks.getWool(color.id()).get()
            );
        }
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            event.register(
                    List.of(BlockTintSources.constant(0xFF000000 | color.hex())),
                    ChromaturgyCarpetBlocks.getCarpet(color.id()).get()
            );
        }
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            event.register(
                    List.of(BlockTintSources.constant(0xFF000000 | ColorLookup.mutedForTerracotta(color.hex()))),
                    ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get()
            );
        }
    }
}