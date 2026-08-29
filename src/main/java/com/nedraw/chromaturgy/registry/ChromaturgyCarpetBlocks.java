package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ChromaturgyCarpetBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Chromaturgy.MODID);

    public static final DeferredRegister.Items BLOCK_ITEMS =
            DeferredRegister.createItems(Chromaturgy.MODID);

    private static final Map<String, DeferredBlock<CarpetBlock>> CARPET_BLOCKS = new LinkedHashMap<>();

    static {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            String registryName = color.id() + "_carpet";
            DeferredBlock<CarpetBlock> block = BLOCKS.registerBlock(
                    registryName,
                    CarpetBlock::new,
                    p -> p.mapColor(MapColor.WOOL).strength(0.1f).sound(SoundType.WOOL).ignitedByLava()
            );
            CARPET_BLOCKS.put(color.id(), block);
            BLOCK_ITEMS.registerSimpleBlockItem(registryName, block);
        }
    }

    private ChromaturgyCarpetBlocks() {}

    public static DeferredBlock<CarpetBlock> getCarpet(String colorId) {
        DeferredBlock<CarpetBlock> block = CARPET_BLOCKS.get(colorId);
        if (block == null) {
            throw new IllegalArgumentException("No registered carpet for color id: " + colorId);
        }
        return block;
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }
}