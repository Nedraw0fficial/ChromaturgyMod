package com.nedraw.chromaturgy.block;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Chromaturgy.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PaintedBlockEntity>> PAINTED =
            BLOCK_ENTITIES.register("painted", () ->
                    new BlockEntityType<>(PaintedBlockEntity::new, ChromaturgyPaintedBlockRegistry.PAINTED_BLOCK.get()));

    private ChromaturgyBlockEntities() {}

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}