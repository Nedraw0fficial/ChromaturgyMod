package com.nedraw.chromaturgy.block;

import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import org.jspecify.annotations.Nullable;

public class PaintedBlockRenderState extends BlockEntityRenderState {
    public @Nullable MovingBlockRenderState originalBlock;
    public int color = 0xFFFFFFFF;
}