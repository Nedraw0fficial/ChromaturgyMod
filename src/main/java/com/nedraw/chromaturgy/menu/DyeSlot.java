package com.nedraw.chromaturgy.menu;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;

public class DyeSlot extends Slot {

    public DyeSlot(Container container, int slotIndex, int x, int y) {
        super(container, slotIndex, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ItemTags.DYES);
    }
}