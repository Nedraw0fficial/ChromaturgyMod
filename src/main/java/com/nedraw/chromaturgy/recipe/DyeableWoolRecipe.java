package com.nedraw.chromaturgy.recipe;

import com.mojang.serialization.MapCodec;
import com.nedraw.chromaturgy.menu.ColorLookup;
import com.nedraw.chromaturgy.menu.WoolColorMatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DyeableWoolRecipe extends CustomRecipe {

    public static final DyeableWoolRecipe INSTANCE = new DyeableWoolRecipe();
    public static final MapCodec<DyeableWoolRecipe> MAP_CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DyeableWoolRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DyeableWoolRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) return false;
        boolean hasWool = false, hasDye = false;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (stack.is(ItemTags.WOOL)) hasWool = true;
            else if (stack.is(ItemTags.DYES)) hasDye = true;
            else return false;
        }
        return hasWool && hasDye;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (!stack.isEmpty() && stack.is(ItemTags.DYES)) {
                return new ItemStack(WoolColorMatch.woolForDye(stack.getItem()));
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<DyeableWoolRecipe> getSerializer() {
        return SERIALIZER;
    }
}