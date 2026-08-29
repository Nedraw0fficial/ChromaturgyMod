package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.registry.ChromaturgyCreativeTabs;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ScreenEvent;

public final class ChromaturgySectionOverlay {

    private static final Identifier BANNER_TEXTURE =
            Identifier.fromNamespaceAndPath("chromaturgy", "textures/gui/section_banner.png");

    private ChromaturgySectionOverlay() {}

    public static void onRenderBackground(ScreenEvent.Render.Background event) {
        if (!(event.getScreen() instanceof CreativeModeInventoryScreen screen)) return;
        if (CreativeModeInventoryScreen.selectedTab != ChromaturgyCreativeTabs.CHROMATURGY_TAB.get()) return;

        int leftPos = screen.getGuiLeft();
        int topPos = screen.getGuiTop();

        for (var header : ChromaturgyCreativeTabs.HEADERS) {
            int screenRow = header.rowIndex() - topVisibleRow(screen);
            if (screenRow < 0 || screenRow >= 5) continue;

            int x = leftPos + 8;
            int y = topPos + 17 + screenRow * 18;

            event.getGuiGraphics().blit(RenderPipelines.GUI_TEXTURED, BANNER_TEXTURE,
                    x, y, 0.0F, 0.0F, 162, 18, 162, 18);
            event.getGuiGraphics().text(net.minecraft.client.Minecraft.getInstance().font,
                    header.title(), x + 4, y + 5, 0xFFFFFFFF, true);
        }
    }

    private static int topVisibleRow(CreativeModeInventoryScreen screen) {
        int itemCount = ChromaturgyCreativeTabs.CHROMATURGY_TAB.get().getDisplayItems().size();
        int rowCount = Math.max(0, (int) Math.ceil(itemCount / 9.0) - 5);
        if (rowCount == 0) return 0;
        return Math.max((int) (screen.scrollOffs * rowCount + 0.5f), 0);
    }

    public static void onItemTooltip(net.neoforged.neoforge.event.entity.player.ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(com.nedraw.chromaturgy.registry.ChromaturgyItems.SECTION_FILLER.get())
                && stack.has(net.minecraft.core.component.DataComponents.CREATIVE_SLOT_LOCK)) {
            event.getToolTip().clear();
        }
    }
}