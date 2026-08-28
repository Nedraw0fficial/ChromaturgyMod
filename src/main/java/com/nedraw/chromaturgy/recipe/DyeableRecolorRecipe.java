package com.nedraw.chromaturgy.recipe;

import com.mojang.serialization.MapCodec;
import com.nedraw.chromaturgy.menu.ColorLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DyeableRecolorRecipe extends CustomRecipe {

    public static final DyeableRecolorRecipe INSTANCE = new DyeableRecolorRecipe();
    public static final MapCodec<DyeableRecolorRecipe> MAP_CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DyeableRecolorRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DyeableRecolorRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private static final java.util.Set<net.minecraft.world.item.Item> DYEABLE_TARGETS = java.util.Set.of(
            net.minecraft.world.item.Items.LEATHER_HELMET,
            net.minecraft.world.item.Items.LEATHER_CHESTPLATE,
            net.minecraft.world.item.Items.LEATHER_LEGGINGS,
            net.minecraft.world.item.Items.LEATHER_BOOTS,
            net.minecraft.world.item.Items.LEATHER_HORSE_ARMOR,
            net.minecraft.world.item.Items.WOLF_ARMOR
    );


    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() < 2) return false;
        boolean hasTarget = false, hasDye = false;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (DYEABLE_TARGETS.contains(stack.getItem())) {
                if (hasTarget) return false;
                hasTarget = true;
            } else if (stack.is(ItemTags.DYES)) {
                hasDye = true;
            } else {
                return false;
            }
        }
        return hasTarget && hasDye;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack targetStack = ItemStack.EMPTY;
        List<Integer> dyeHexes = new ArrayList<>();

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (DYEABLE_TARGETS.contains(stack.getItem())) {
                if (!targetStack.isEmpty()) return ItemStack.EMPTY;
                targetStack = stack;
            } else if (stack.is(ItemTags.DYES)) {
                dyeHexes.add(ColorLookup.hexOf(stack.getItem()));
            } else {
                return ItemStack.EMPTY;
            }
        }
        if (targetStack.isEmpty() || dyeHexes.isEmpty()) return ItemStack.EMPTY;

        DyedItemColor currentDye = targetStack.get(DataComponents.DYED_COLOR);
        int redTotal = 0, greenTotal = 0, blueTotal = 0, intensityTotal = 0, count = 0;

        if (currentDye != null) {
            int r = ARGB.red(currentDye.rgb()), g = ARGB.green(currentDye.rgb()), b = ARGB.blue(currentDye.rgb());
            intensityTotal += Math.max(r, Math.max(g, b));
            redTotal += r; greenTotal += g; blueTotal += b;
            count++;
        }
        for (int hex : dyeHexes) {
            int r = (hex >> 16) & 0xFF, g = (hex >> 8) & 0xFF, b = hex & 0xFF;
            intensityTotal += Math.max(r, Math.max(g, b));
            redTotal += r; greenTotal += g; blueTotal += b;
            count++;
        }

        int red = redTotal / count, green = greenTotal / count, blue = blueTotal / count;
        float averageIntensity = (float) intensityTotal / count;
        float resultIntensity = Math.max(red, Math.max(green, blue));
        if (resultIntensity > 0) {
            red = (int) (red * averageIntensity / resultIntensity);
            green = (int) (green * averageIntensity / resultIntensity);
            blue = (int) (blue * averageIntensity / resultIntensity);
        }

        ItemStack result = targetStack.copyWithCount(1);
        result.set(DataComponents.DYED_COLOR, new DyedItemColor(ARGB.color(0, red, green, blue)));
        return result;
    }

    @Override
    public RecipeSerializer<DyeableRecolorRecipe> getSerializer() {
        return SERIALIZER;
    }

}