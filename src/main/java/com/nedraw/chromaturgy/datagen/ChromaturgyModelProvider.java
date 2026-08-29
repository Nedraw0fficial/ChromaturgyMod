package com.nedraw.chromaturgy.datagen;

import com.nedraw.chromaturgy.Chromaturgy;
import com.nedraw.chromaturgy.ChromaturgyDyeColor;
import com.nedraw.chromaturgy.menu.ColorLookup;
import com.nedraw.chromaturgy.registry.*;
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

    private static final ModelTemplate TINTED_CUBE_ALL = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "block/tinted_cube_all")),
            Optional.empty(),
            TextureSlot.ALL
    );

    private static final TextureSlot WOOL_SLOT = TextureSlot.create("wool");

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

        Item sectionFiller = ChromaturgyItems.SECTION_FILLER.get();
        TextureMapping sectionFillerMapping = new TextureMapping()
                .put(TextureSlot.LAYER0, new Material(Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "item/section_filler")));
        Identifier sectionFillerModel = ModelTemplates.FLAT_ITEM.create(sectionFiller, sectionFillerMapping, itemModels.modelOutput);
        itemModels.itemModelOutput.accept(sectionFiller, ItemModelUtils.plainModel(sectionFillerModel));

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

        Identifier vanillaWoolTexture = Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "block/wool_template");

        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            Block woolBlock = ChromaturgyWoolBlocks.getWool(color.id()).get();

            Identifier woolModel = TINTED_CUBE_ALL.create(
                    woolBlock,
                    new TextureMapping().put(TextureSlot.ALL, new Material(vanillaWoolTexture)),
                    blockModels.modelOutput
            );

            blockModels.blockStateOutput.accept(
                    BlockModelGenerators.createSimpleBlock(woolBlock, BlockModelGenerators.plainVariant(woolModel))
            );

            blockModels.registerSimpleTintedItemModel(woolBlock, woolModel, ItemModelUtils.constantTint(0xFF000000 | color.hex()));
        }

        Identifier carpetTemplate = Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "block/tinted_carpet");

        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            Block carpetBlock = ChromaturgyCarpetBlocks.getCarpet(color.id()).get();

            ModelTemplate carpetModelTemplate = new ModelTemplate(
                    Optional.of(carpetTemplate),
                    Optional.empty(),
                    WOOL_SLOT
            );

            Identifier carpetModel = carpetModelTemplate.create(
                    carpetBlock,
                    new TextureMapping().put(WOOL_SLOT, new Material(vanillaWoolTexture)),
                    blockModels.modelOutput
            );

            blockModels.blockStateOutput.accept(
                    BlockModelGenerators.createSimpleBlock(carpetBlock, BlockModelGenerators.plainVariant(carpetModel))
            );

            blockModels.registerSimpleTintedItemModel(carpetBlock, carpetModel, ItemModelUtils.constantTint(0xFF000000 | color.hex()));
        }

        Identifier terracottaTexture = Identifier.fromNamespaceAndPath(Chromaturgy.MODID, "block/terracotta_template");

        for (ChromaturgyDyeColor color : ColorDefinitions.all()) {
            Block terracottaBlock = ChromaturgyTerracottaBlocks.getTerracotta(color.id()).get();

            Identifier terracottaModel = TINTED_CUBE_ALL.create(
                    terracottaBlock,
                    new TextureMapping().put(TextureSlot.ALL, new Material(terracottaTexture)),
                    blockModels.modelOutput
            );

            blockModels.blockStateOutput.accept(
                    BlockModelGenerators.createSimpleBlock(terracottaBlock, BlockModelGenerators.plainVariant(terracottaModel))
            );
            blockModels.registerSimpleTintedItemModel(terracottaBlock, terracottaModel,
                    ItemModelUtils.constantTint(0xFF000000 | ColorLookup.mutedForTerracotta(color.hex())));
        }
    }
}