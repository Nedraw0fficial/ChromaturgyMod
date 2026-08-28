package com.nedraw.chromaturgy.menu;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class MixButton extends Button {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "textures/gui/pigment_station_button.png");

    private boolean pressedVisual = false;

    public MixButton(int x, int y, OnPress onPress) {
        super(x, y, 42, 20, Component.translatable("gui.chromaturgy.mix"), onPress, DEFAULT_NARRATION);
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        this.pressedVisual = true;
        super.onClick(event, doubleClick);
    }

    @Override
    public void onRelease(MouseButtonEvent event) {
        this.pressedVisual = false;
        super.onRelease(event);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int vOffset = this.pressedVisual ? 40 : (this.isHovered() ? 20 : 0);
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.getX(), this.getY(),
                0.0F, (float) vOffset, this.getWidth(), this.getHeight(), 42, 60);

        ActiveTextCollector textCollector = graphics.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE);
        this.extractDefaultLabel(textCollector);
    }
}