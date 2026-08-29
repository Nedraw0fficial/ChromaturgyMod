package com.nedraw.chromaturgy.recipe;

import com.mojang.serialization.MapCodec;
import com.nedraw.chromaturgy.menu.CarpetColorMatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class WoolToCarpetRecipe extends CustomRecipe {

    public static final WoolToCarpetRecipe INSTANCE = new WoolToCarpetRecipe();
    public static final MapCodec<WoolToCarpetRecipe> MAP_CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, WoolToCarpetRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<WoolToCarpetRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput input, Level level) {
        Item first = null;
        int count = 0;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (!stack.is(ItemTags.WOOL)) return false;
            if (first == null) first = stack.getItem();
            else if (stack.getItem() != first) return false;
            count++;
        }
        return count == 2 && first != null && CarpetColorMatch.isKnownWool(first);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (!stack.isEmpty()) {
                return new ItemStack(CarpetColorMatch.carpetForWool(stack.getItem()), 3);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<WoolToCarpetRecipe> getSerializer() {
        return SERIALIZER;
    }
}