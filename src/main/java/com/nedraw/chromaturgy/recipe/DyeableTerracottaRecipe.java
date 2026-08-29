package com.nedraw.chromaturgy.recipe;

import com.mojang.serialization.MapCodec;
import com.nedraw.chromaturgy.menu.TerracottaColorMatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DyeableTerracottaRecipe extends CustomRecipe {

    public static final DyeableTerracottaRecipe INSTANCE = new DyeableTerracottaRecipe();
    public static final MapCodec<DyeableTerracottaRecipe> MAP_CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DyeableTerracottaRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DyeableTerracottaRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int terracottaCount = 0, dyeCount = 0;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (stack.is(ItemTags.TERRACOTTA)) terracottaCount++;
            else if (stack.is(ItemTags.DYES)) dyeCount++;
            else return false;
        }
        return terracottaCount >= 1 && dyeCount == 1;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        int terracottaCount = 0;
        var target = net.minecraft.world.item.Items.WHITE_TERRACOTTA;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (stack.is(ItemTags.TERRACOTTA)) terracottaCount++;
            else if (stack.is(ItemTags.DYES)) target = TerracottaColorMatch.terracottaForDye(stack.getItem());
        }
        return new ItemStack(target, terracottaCount);
    }

    @Override
    public RecipeSerializer<DyeableTerracottaRecipe> getSerializer() {
        return SERIALIZER;
    }
}