package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyCarpetBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyTerracottaBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyWoolBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;

import java.util.Set;

public class ChromaturgyBlockLootProvider extends BlockLootSubProvider {

    public ChromaturgyBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.<Item>of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected void generate() {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            dropSelf(ChromaturgyWoolBlocks.getWool(color.id()).get());
            dropSelf(ChromaturgyCarpetBlocks.getCarpet(color.id()).get());
            dropSelf(ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get());
        }
    }

    @Override
    protected Iterable<net.minecraft.world.level.block.Block> getKnownBlocks() {
        java.util.List<net.minecraft.world.level.block.Block> blocks = new java.util.ArrayList<>();
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            blocks.add(ChromaturgyWoolBlocks.getWool(color.id()).get());
            blocks.add(ChromaturgyCarpetBlocks.getCarpet(color.id()).get());
            blocks.add(ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get());
        }
        return blocks;
    }
}