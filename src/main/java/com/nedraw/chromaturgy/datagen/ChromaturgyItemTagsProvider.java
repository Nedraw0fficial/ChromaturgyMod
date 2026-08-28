package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ChromaturgyWoolBlocks;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ChromaturgyItemTagsProvider extends ItemTagsProvider {

    public ChromaturgyItemTagsProvider(PackOutput output,
                                       CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Chromaturgy.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            tag(ItemTags.DYES).add(ChromaturgyItems.getDye(color.id()).get());
            tag(ItemTags.WOOL).add(ChromaturgyWoolBlocks.getWool(color.id()).get().asItem());
        }
    }
}