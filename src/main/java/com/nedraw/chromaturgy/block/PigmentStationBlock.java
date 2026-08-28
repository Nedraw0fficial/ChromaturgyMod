package com.nedraw.chromaturgy.block;

import com.nedraw.chromaturgy.menu.PigmentStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PigmentStationBlock extends Block {

    public PigmentStationBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            player.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, p) -> new PigmentStationMenu(containerId, playerInventory),
                    Component.translatable("block.chromaturgy.pigment_station")
            ));
        }
        return InteractionResult.SUCCESS;
    }
}