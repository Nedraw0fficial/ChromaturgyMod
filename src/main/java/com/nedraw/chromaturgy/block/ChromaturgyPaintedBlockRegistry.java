package com.nedraw.chromaturgy.block;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyPaintedBlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Chromaturgy.MODID);

    public static final DeferredBlock<PaintedBlock> PAINTED_BLOCK = BLOCKS.registerBlock(
            "painted_block",
            PaintedBlock::new,
            p -> p.mapColor(MapColor.STONE).strength(1.5f).sound(SoundType.STONE).noOcclusion()
    );

    private ChromaturgyPaintedBlockRegistry() {}

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}