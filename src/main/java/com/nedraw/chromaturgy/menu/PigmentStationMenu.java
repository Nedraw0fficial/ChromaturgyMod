package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PigmentStationMenu extends AbstractContainerMenu {

    public static final int SWATCH_SLOT = 0;
    public static final int DYE_SLOT_1 = 1;
    public static final int DYE_SLOT_2 = 2;
    public static final int DYE_SLOT_3 = 3;
    public static final int OUTPUT_SLOT = 4;

    private final Container inputContainer = new SimpleContainer(5);
    private final Player player;

    public PigmentStationMenu(int containerId, Inventory playerInventory) {
        super(ChromaturgyMenuTypes.PIGMENT_STATION.get(), containerId);
        this.player = playerInventory.player;

        this.addSlot(new SwatchCardSlot(inputContainer, SWATCH_SLOT, 17, 48));
        this.addSlot(new DyeSlot(inputContainer, DYE_SLOT_1, 44, 48));
        this.addSlot(new DyeSlot(inputContainer, DYE_SLOT_2, 62, 48));
        this.addSlot(new DyeSlot(inputContainer, DYE_SLOT_3, 80, 48));
        this.addSlot(new Slot(inputContainer, OUTPUT_SLOT, 134, 48) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public boolean canMix() {
        boolean hasSwatch = !inputContainer.getItem(SWATCH_SLOT).isEmpty();
        int dyeCount = 0;
        for (int i = DYE_SLOT_1; i <= DYE_SLOT_3; i++) {
            if (!inputContainer.getItem(i).isEmpty()) dyeCount++;
        }
        boolean outputEmpty = inputContainer.getItem(OUTPUT_SLOT).isEmpty();
        return hasSwatch && dyeCount >= 2 && outputEmpty;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == 0 && canMix()) {
            mix();
            return true;
        }
        return false;
    }

    private void mix() {
        float sinSum = 0, cosSum = 0, sSum = 0, lSum = 0;
        int count = 0;

        for (int i = DYE_SLOT_1; i <= DYE_SLOT_3; i++) {
            ItemStack stack = inputContainer.getItem(i);
            if (!stack.isEmpty()) {
                int hex = ColorLookup.hexOf(stack.getItem());
                int rr = (hex >> 16) & 0xFF, gg = (hex >> 8) & 0xFF, bb = hex & 0xFF;
                float[] hsl = ColorLookup.rgbToHsl(rr, gg, bb);
                float chroma = (Math.max(rr, Math.max(gg, bb)) - Math.min(rr, Math.min(gg, bb))) / 255f;

                double rad = Math.toRadians(hsl[0]);
                sinSum += Math.sin(rad) * chroma;
                cosSum += Math.cos(rad) * chroma;
                sSum += hsl[1];
                lSum += hsl[2];
                count++;
            }
        }

        float avgHue;
        if (sinSum == 0 && cosSum == 0) {
            avgHue = 0;
        } else {
            avgHue = (float) Math.toDegrees(Math.atan2(sinSum, cosSum));
            if (avgHue < 0) avgHue += 360;
        }
        float avgSat = sSum / count;
        float avgLight = lSum / count;

        int[] rgb = ColorLookup.hslToRgb(avgHue, avgSat, avgLight);
        ItemStack result = new ItemStack(ColorLookup.closestMatch(rgb[0], rgb[1], rgb[2]), count);

        for (int i = DYE_SLOT_1; i <= DYE_SLOT_3; i++) {
            ItemStack stack = inputContainer.getItem(i);
            if (!stack.isEmpty()) stack.shrink(1);
        }
        inputContainer.getItem(SWATCH_SLOT).shrink(1);
        inputContainer.setItem(OUTPUT_SLOT, result);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.clearContainer(player, this.inputContainer);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            result = slotStack.copy();

            if (index < 5) {

                if (!this.moveItemStackTo(slotStack, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotStack.is(ChromaturgyItems.SWATCH_CARD.get())) {
                if (!this.moveItemStackTo(slotStack, SWATCH_SLOT, SWATCH_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotStack.is(net.minecraft.tags.ItemTags.DYES)) {
                if (!this.moveItemStackTo(slotStack, DYE_SLOT_1, OUTPUT_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == result.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return true; // transient container
    }
}