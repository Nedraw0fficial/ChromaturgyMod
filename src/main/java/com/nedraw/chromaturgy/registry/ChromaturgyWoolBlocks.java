package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ChromaturgyWoolBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Chromaturgy.MODID);

    public static final DeferredRegister.Items BLOCK_ITEMS =
            DeferredRegister.createItems(Chromaturgy.MODID);

    private static final Map<String, DeferredBlock<Block>> WOOL_BLOCKS = new LinkedHashMap<>();

    static {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            String registryName = color.id() + "_wool";
            DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(
                    registryName,
                    p -> p.mapColor(MapColor.WOOL).strength(0.8f).sound(SoundType.WOOL).ignitedByLava().instrument(NoteBlockInstrument.GUITAR)
            );
            WOOL_BLOCKS.put(color.id(), block);
            BLOCK_ITEMS.registerSimpleBlockItem(registryName, block);
        }
    }

    private ChromaturgyWoolBlocks() {}

    public static DeferredBlock<Block> getWool(String colorId) {
        DeferredBlock<Block> block = WOOL_BLOCKS.get(colorId);
        if (block == null) {
            throw new IllegalArgumentException("No registered wool for color id: " + colorId);
        }
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }
}