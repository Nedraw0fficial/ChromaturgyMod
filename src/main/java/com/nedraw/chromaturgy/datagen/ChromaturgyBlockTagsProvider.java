package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyCarpetBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyTerracottaBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyWoolBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ChromaturgyBlockTagsProvider extends BlockTagsProvider {

    public ChromaturgyBlockTagsProvider(PackOutput output,
                                        CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Chromaturgy.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            tag(BlockTags.WOOL).add(ChromaturgyWoolBlocks.getWool(color.id()).get());
            tag(BlockTags.WOOL_CARPETS).add(ChromaturgyCarpetBlocks.getCarpet(color.id()).get());
            tag(BlockTags.DAMPENS_VIBRATIONS).add(ChromaturgyWoolBlocks.getWool(color.id()).get());
            tag(BlockTags.DAMPENS_VIBRATIONS).add(ChromaturgyCarpetBlocks.getCarpet(color.id()).get());
            tag(BlockTags.TERRACOTTA).add(ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get());
        }
    }
}