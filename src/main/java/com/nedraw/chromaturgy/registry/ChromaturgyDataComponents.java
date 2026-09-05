package com.nedraw.chromaturgy.registry;

import com.mojang.serialization.Codec;
import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyDataComponents {

    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Chromaturgy.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> PAINT_CHARGES =
            COMPONENTS.registerComponentType("paint_charges", builder ->
                    builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    private ChromaturgyDataComponents() {}

    public static void register(IEventBus modEventBus) {
        COMPONENTS.register(modEventBus);
    }
}