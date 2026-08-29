package com.nedraw.chromaturgy.registry;

import com.nedraw.chromaturgy.Chromaturgy;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public final class ChromaturgyCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Chromaturgy.MODID);

    public record SectionHeader(int rowIndex, Component title) {}
    public static final List<SectionHeader> HEADERS = new ArrayList<>();

    private static int fillerCounter = 0;

    private static ItemStack filler() {
        ItemStack stack = new ItemStack(ChromaturgyItems.SECTION_FILLER.get());
        stack.set(DataComponents.CREATIVE_SLOT_LOCK, Unit.INSTANCE);
        stack.set(DataComponents.ITEM_NAME, Component.literal("chromaturgy_filler_" + (fillerCounter++)));
        return stack;
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CHROMATURGY_TAB =
            CREATIVE_MODE_TABS.register("chromaturgy_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.chromaturgy"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ChromaturgyItems.getDye("vermilion").get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        HEADERS.clear();
                        int[] count = {0};

                        Runnable padToRowStart = () -> {
                            while (count[0] % 9 != 0) {
                                output.accept(filler());
                                count[0]++;
                            }
                        };

                        java.util.function.BiConsumer<Component, Runnable> section = (title, body) -> {
                            padToRowStart.run();
                            HEADERS.add(new SectionHeader(count[0] / 9, title));
                            for (int i = 0; i < 9; i++) { output.accept(filler()); count[0]++; }
                            body.run();
                        };

                        section.accept(Component.translatable("itemGroup.chromaturgy.special"), () -> {
                            output.accept(ChromaturgyBlocks.PIGMENT_STATION_ITEM.get()); count[0]++;
                            output.accept(ChromaturgyItems.SWATCH_CARD.get()); count[0]++;
                        });

                        section.accept(Component.translatable("itemGroup.chromaturgy.dyes"), () -> {
                            List<com.nedraw.chromaturgy.ChromaturgyDyeColor> sorted =
                                    new ArrayList<>(ColorDefinitions.all());
                            sorted.sort(java.util.Comparator.comparingDouble(c ->
                                    com.nedraw.chromaturgy.menu.ColorLookup.rgbToHsl(c.red(), c.green(), c.blue())[0]));
                            for (var color : sorted) {
                                output.accept(ChromaturgyItems.getDye(color.id()).get());
                                count[0]++;
                            }
                        });

                        section.accept(Component.translatable("itemGroup.chromaturgy.wool"), () -> {
                            for (var color : ColorDefinitions.all()) {
                                output.accept(ChromaturgyWoolBlocks.getWool(color.id()).get().asItem());
                                count[0]++;
                            }
                        });
                    })
                    .build());

    private ChromaturgyCreativeTabs() {}

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}