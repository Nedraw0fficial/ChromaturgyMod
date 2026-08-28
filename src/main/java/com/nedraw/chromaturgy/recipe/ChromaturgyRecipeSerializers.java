package com.nedraw.chromaturgy.recipe;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ChromaturgyRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Chromaturgy.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeableRecolorRecipe>> DYEABLE_RECOLOR =
            RECIPE_SERIALIZERS.register("dyeable_recolor", () -> DyeableRecolorRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeableFireworkStarRecipe>> DYEABLE_FIREWORK_STAR =
            RECIPE_SERIALIZERS.register("dyeable_firework_star", () -> DyeableFireworkStarRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeableWoolRecipe>> DYEABLE_WOOL =
            RECIPE_SERIALIZERS.register("dyeable_wool", () -> DyeableWoolRecipe.SERIALIZER);

    private ChromaturgyRecipeSerializers() {}

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }

}