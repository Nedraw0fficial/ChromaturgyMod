package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Container;

public class SwatchCardSlot extends Slot {

    public SwatchCardSlot(Container container, int slotIndex, int x, int y) {
        super(container, slotIndex, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(ChromaturgyItems.SWATCH_CARD.get());
    }
}