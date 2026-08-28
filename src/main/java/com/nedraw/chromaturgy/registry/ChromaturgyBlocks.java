package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.block.PigmentStationBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Chromaturgy.MODID);

    public static final DeferredRegister.Items BLOCK_ITEMS =
            DeferredRegister.createItems(Chromaturgy.MODID);

    public static final DeferredBlock<PigmentStationBlock> PIGMENT_STATION = BLOCKS.registerBlock(
            "pigment_station",
            PigmentStationBlock::new,
            p -> p.mapColor(MapColor.WOOD).strength(2.5f).sound(SoundType.WOOD)
    );

    public static final DeferredHolder<net.minecraft.world.item.Item, BlockItem> PIGMENT_STATION_ITEM =
            BLOCK_ITEMS.registerSimpleBlockItem("pigment_station", PIGMENT_STATION);

    private ChromaturgyBlocks() {}

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
    }
}