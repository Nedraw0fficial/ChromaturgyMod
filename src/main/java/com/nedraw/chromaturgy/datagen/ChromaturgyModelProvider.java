package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;

public class ChromaturgyModelProvider extends ModelProvider {

    private static final ModelTemplate PAPER_BACKED_DYE = new ModelTemplate(
            Optional.of(Identifier.withDefaultNamespace("item/generated")),
            Optional.empty(),
            TextureSlot.LAYER0, TextureSlot.LAYER1
    );

    public ChromaturgyModelProvider(PackOutput output) {
        super(output, Chromaturgy.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            Item item = ChromaturgyItems.getDye(color.id()).get();

            if (color.customTexture()) {

                itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            } else {

                Identifier modelLoc = PAPER_BACKED_DYE.create(
                        item,
                        new TextureMapping()
                                .put(TextureSlot.LAYER0, new net.minecraft.client.resources.model.sprite.Material(
                                        Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "item/dye_paper")))
                                .put(TextureSlot.LAYER1, new net.minecraft.client.resources.model.sprite.Material(
                                        Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "item/dye_placeholder"))),
                        itemModels.modelOutput
                );

                itemModels.itemModelOutput.accept(
                        item,
                        new CuboidItemModelWrapper.Unbaked(
                                modelLoc,
                                Optional.empty(),
                                List.of(
                                        new Constant(0xFFFFFF), //paper
                                        new Constant(color.hex())
                                )
                        )
                );
            }
        }
    }
}