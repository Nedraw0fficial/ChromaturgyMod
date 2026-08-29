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

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeableCarpetRecipe>> DYEABLE_CARPET =
            RECIPE_SERIALIZERS.register("dyeable_carpet", () -> DyeableCarpetRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WoolToCarpetRecipe>> WOOL_TO_CARPET =
            RECIPE_SERIALIZERS.register("wool_to_carpet", () -> WoolToCarpetRecipe.SERIALIZER);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeableTerracottaRecipe>> DYEABLE_TERRACOTTA =
            RECIPE_SERIALIZERS.register("dyeable_terracotta", () -> DyeableTerracottaRecipe.SERIALIZER);


    private ChromaturgyRecipeSerializers() {}

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }

}