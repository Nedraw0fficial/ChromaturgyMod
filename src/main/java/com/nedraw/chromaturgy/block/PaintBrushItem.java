package com.nedraw.chromaturgy.block;

import com.nedraw.chromaturgy.registry.ChromaturgyDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;

public class PaintBrushItem extends Item {

    public PaintBrushItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (level.isClientSide() || player == null) return InteractionResult.SUCCESS;

        BlockState targetState = level.getBlockState(pos);
        Block targetBlock = targetState.getBlock();

        if (!Block.isShapeFullBlock(targetState.getShape(level, pos))) {
            return InteractionResult.FAIL;
        }

        DyedItemColor dyedColor = stack.get(DataComponents.DYED_COLOR);
        if (dyedColor == null) return InteractionResult.FAIL;
        int color = dyedColor.rgb();

        if (targetBlock instanceof PaintedBlock) {
            if (level.getBlockEntity(pos) instanceof PaintedBlockEntity entity) {
                entity.setColor(color);
            }
        } else {
            level.setBlock(pos, ChromaturgyPaintedBlockRegistry.PAINTED_BLOCK.get().defaultBlockState(), 3);
            if (level.getBlockEntity(pos) instanceof PaintedBlockEntity entity) {
                entity.setOriginalState(targetState);
                entity.setColor(color);
            }
        }

        int charges = stack.getOrDefault(ChromaturgyDataComponents.PAINT_CHARGES.get(), 96) - 1;
        if (charges <= 0) {
            player.setItemInHand(context.getHand(), new ItemStack(Items.BRUSH));
        } else {
            stack.set(ChromaturgyDataComponents.PAINT_CHARGES.get(), charges);
        }

        return InteractionResult.SUCCESS;
    }
}