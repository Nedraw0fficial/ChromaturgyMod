package com.nedraw.chromaturgy.recipe;

import com.mojang.serialization.MapCodec;
import com.nedraw.chromaturgy.menu.ColorLookup;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DyeableFireworkStarRecipe extends CustomRecipe {

    public static final DyeableFireworkStarRecipe INSTANCE = new DyeableFireworkStarRecipe();
    public static final MapCodec<DyeableFireworkStarRecipe> MAP_CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DyeableFireworkStarRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DyeableFireworkStarRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() < 2) return false;
        boolean hasFuel = false, hasDye = false;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (stack.is(Items.GUNPOWDER)) {
                if (hasFuel) return false;
                hasFuel = true;
            } else if (stack.is(ItemTags.DYES)) {
                hasDye = true;
            } else {
                return false;
            }
        }
        return hasFuel && hasDye;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        IntList colors = new IntArrayList();

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (!stack.isEmpty() && stack.is(ItemTags.DYES)) {
                colors.add(ColorLookup.hexOf(stack.getItem()));
            }
        }
        if (colors.isEmpty()) return ItemStack.EMPTY;

        ItemStack star = new ItemStack(Items.FIREWORK_STAR);
        star.set(DataComponents.FIREWORK_EXPLOSION,
                new FireworkExplosion(FireworkExplosion.Shape.SMALL_BALL, colors, IntList.of(), false, false));
        return star;
    }

    @Override
    public RecipeSerializer<DyeableFireworkStarRecipe> getSerializer() {
        return SERIALIZER;
    }
}