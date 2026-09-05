package com.nedraw.chromaturgy.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;

import javax.annotation.Nullable;

public class PaintedBlockEntity extends BlockEntity {

    private BlockState originalState;
    private int color = 0xFFFFFF;

    public PaintedBlockEntity(BlockPos pos, BlockState blockState) {
        super(ChromaturgyBlockEntities.PAINTED.get(), pos, blockState);
    }

    public BlockState getOriginalState() {
        return originalState;
    }

    public void setOriginalState(BlockState state) {
        this.originalState = state;
        setChanged();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("color", color);
        if (originalState != null) {
            output.store("original_state", BlockState.CODEC, originalState);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        color = input.getIntOr("color", 0xFFFFFF);
        originalState = input.read("original_state", BlockState.CODEC).orElse(null);
    }

    @Override
    public net.minecraft.nbt.CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}