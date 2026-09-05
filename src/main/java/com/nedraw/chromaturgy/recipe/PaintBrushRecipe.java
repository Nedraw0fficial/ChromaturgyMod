package com.nedraw.chromaturgy.recipe;

import com.mojang.serialization.MapCodec;
import com.nedraw.chromaturgy.menu.ColorLookup;
import com.nedraw.chromaturgy.registry.ChromaturgyDataComponents;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;

public class PaintBrushRecipe extends CustomRecipe {

    public static final PaintBrushRecipe INSTANCE = new PaintBrushRecipe();
    public static final MapCodec<PaintBrushRecipe> MAP_CODEC = MapCodec.unit(() -> INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, PaintBrushRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<PaintBrushRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) return false;
        boolean hasBrush = false, hasDye = false;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.isEmpty()) continue;
            if (stack.is(Items.BRUSH)) hasBrush = true;
            else if (stack.is(ItemTags.DYES)) hasDye = true;
            else return false;
        }
        return hasBrush && hasDye;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (!stack.isEmpty() && stack.is(ItemTags.DYES)) {
                int hex = ColorLookup.hexOf(stack.getItem());
                ItemStack result = new ItemStack(ChromaturgyItems.PAINT_BRUSH.get());
                result.set(DataComponents.DYED_COLOR, new DyedItemColor(hex));
                result.set(DataComponents.TOOLTIP_DISPLAY,
                        TooltipDisplay.DEFAULT.withHidden(DataComponents.DYED_COLOR, true));
                result.set(ChromaturgyDataComponents.PAINT_CHARGES.get(), 96);

                Component colorName = stack.getHoverName();
                Component loreLine = Component.translatable("chromaturgy.paint_brush.color_lore", colorName)
                        .withStyle(net.minecraft.network.chat.Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(false));
                result.set(DataComponents.LORE, new ItemLore(List.of(loreLine)));
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<PaintBrushRecipe> getSerializer() {
        return SERIALIZER;
    }
}