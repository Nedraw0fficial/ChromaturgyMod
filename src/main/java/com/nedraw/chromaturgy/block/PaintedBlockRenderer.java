package com.nedraw.chromaturgy.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class PaintedBlockRenderer implements BlockEntityRenderer<PaintedBlockEntity, PaintedBlockRenderState> {

    @Override
    public PaintedBlockRenderState createRenderState() {
        return new PaintedBlockRenderState();
    }

    @Override
    public void extractRenderState(PaintedBlockEntity blockEntity, PaintedBlockRenderState state,
                                   float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.color = blockEntity.getColor();
        state.originalBlock = null;

        BlockState originalState = blockEntity.getOriginalState();
        Level level = blockEntity.getLevel();
        if (originalState != null && level instanceof ClientLevel clientLevel) {
            BlockPos pos = blockEntity.getBlockPos();
            Holder<Biome> biome = clientLevel.getBiome(pos);

            MovingBlockRenderState renderState = new MovingBlockRenderState();
            renderState.randomSeedPos = pos;
            renderState.blockPos = pos;
            renderState.blockState = originalState;
            renderState.biome = biome;
            renderState.cardinalLighting = clientLevel.cardinalLighting();
            renderState.lightEngine = clientLevel.getLightEngine();
            state.originalBlock = renderState;
        }
    }

    @Override
    public void submit(PaintedBlockRenderState state, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.originalBlock != null) {
            submitNodeCollector.submitMovingBlock(poseStack, state.originalBlock);
        }
        // TODO: tinted overlay quad on top, using state.color
    }

    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(PaintedBlockEntity blockEntity) {
        return net.minecraft.world.phys.AABB.INFINITE;
    }
}