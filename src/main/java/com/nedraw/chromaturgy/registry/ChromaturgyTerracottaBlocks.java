package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ChromaturgyTerracottaBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Chromaturgy.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS =
            DeferredRegister.createItems(Chromaturgy.MODID);

    private static final Map<String, DeferredBlock<Block>> TERRACOTTA_BLOCKS = new LinkedHashMap<>();

    static {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            String registryName = color.id() + "_terracotta";
            DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(
                    registryName,
                    p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)
                            .strength(1.25f, 4.2f)
                            .sound(SoundType.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
            );
            TERRACOTTA_BLOCKS.put(color.id(), block);
            BLOCK_ITEMS.registerSimpleBlockItem(registryName, block);
        }
    }

    private ChromaturgyTerracottaBlocks() {}

    public static DeferredBlock<Block> getTerracotta(String colorId) {
        DeferredBlock<Block> block = TERRACOTTA_BLOCKS.get(colorId);
        if (block == null) {
            throw new IllegalArgumentException("No registered terracotta for color id: " + colorId);
        }
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }
}