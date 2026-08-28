package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class PigmentStationScreen extends AbstractContainerScreen<PigmentStationMenu> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "textures/gui/pigment_station.png");

    public PigmentStationScreen(PigmentStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.titleLabelX = 44;
        this.titleLabelY = 15;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new MixButton(
                this.leftPos + 121,
                this.topPos + 21,
                button -> {
                    net.minecraft.client.Minecraft.getInstance().gameMode
                            .handleInventoryButtonClick(this.menu.containerId, 0);
                }
        ));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int xo = this.leftPos;
        int yo = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, xo, yo, 0.0F, 0.0F,
                this.imageWidth, this.imageHeight, 176, 166);
    }
}