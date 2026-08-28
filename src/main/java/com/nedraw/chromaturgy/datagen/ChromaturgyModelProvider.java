package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.registry.ChromaturgyBlocks;
import com.nedraw.chromaturgy.registry.ChromaturgyItems;
import com.nedraw.chromaturgy.registry.ColorDefinitions;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

        Block pigmentStation = ChromaturgyBlocks.PIGMENT_STATION.get();
        TextureMapping pigmentStationMapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(pigmentStation, "_side_north"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(pigmentStation, "_bottom"))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(pigmentStation, "_top"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(pigmentStation, "_side_north"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(pigmentStation, "_side_south"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(pigmentStation, "_side_east"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(pigmentStation, "_side_west"));

        Identifier pigmentStationModel = ModelTemplates.CUBE.create(pigmentStation, pigmentStationMapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(pigmentStation, BlockModelGenerators.plainVariant(pigmentStationModel))
        );

        Item swatchCard = ChromaturgyItems.SWATCH_CARD.get();
        TextureMapping swatchCardMapping = new TextureMapping()
                .put(TextureSlot.LAYER0, new Material(Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "item/swatch_card")));
        Identifier swatchCardModel = ModelTemplates.FLAT_ITEM.create(swatchCard, swatchCardMapping, itemModels.modelOutput);
        itemModels.itemModelOutput.accept(swatchCard, ItemModelUtils.plainModel(swatchCardModel));

        blockModels.registerSimpleItemModel(pigmentStation, pigmentStationModel);
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