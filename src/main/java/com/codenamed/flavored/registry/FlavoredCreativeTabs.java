package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Flavored.MOD_ID);

    public static final Supplier<CreativeModeTab> FLAVORED = CREATIVE_MODE_TAB.register("flavored",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.APPLE))
                    .title(Component.translatable("creativetab.flavored.flavored"))
                    .displayItems((itemDisplayParameters, output) -> {


                        //Building

                        output.accept(FlavoredBlocks.ROCK_SALT);
                        output.accept(FlavoredBlocks.BUDDING_ROCK_SALT);
                        output.accept(FlavoredBlocks.SALT_CLUSTER);
                        output.accept(FlavoredBlocks.SALT_BLOCK);
                        output.accept(FlavoredBlocks.CHOCOLATE_BLOCK);

                        //Functional

                        //Workstations

                        output.accept(FlavoredBlocks.FERMENTER);

                        //Misc

                        output.accept(FlavoredBlocks.SALT_LAMP);

                        //Wild Crops

                        output.accept(FlavoredBlocks.WILD_CUCUMBER);
                        output.accept(FlavoredBlocks.WILD_PEPPER);
                        output.accept(FlavoredBlocks.WILD_GARLIC);

                        //Ingredients

                        //Crops
                        output.accept(FlavoredItems.TOMATO);
                        output.accept(FlavoredItems.CUCUMBER);
                        output.accept(FlavoredItems.PEPPER);
                        output.accept(FlavoredItems.GARLIC);

                        //Misc

                        output.accept(FlavoredItems.FRESH_PASTA);
                        output.accept(FlavoredItems.SALT);
                        output.accept(FlavoredItems.CHOCOLATE);
                        output.accept(FlavoredBlocks.RAW_CHEESE);
                        output.accept(FlavoredBlocks.CHEESE);
                        output.accept(FlavoredItems.CHEESE_SLICE);

                        //Seeds

                        output.accept(FlavoredItems.TOMATO_SEEDS);
                        output.accept(FlavoredItems.CUCUMBER_SEEDS);
                        output.accept(FlavoredItems.PEPPER_SEEDS);


                        //Foodstuffs

                        //Enhanced

                        output.accept(FlavoredItems.DRIED_PEPPER);
                        output.accept(FlavoredItems.PICKLED_BEETROOT);
                        output.accept(FlavoredItems.PICKLED_CUCUMBER);
                        output.accept(FlavoredItems.PICKLED_EGG);

                        //Normal

                        //Snacks

                        //Dishes

                        output.accept(FlavoredItems.TOMATO_SAUCE_PASTA);
                        output.accept(FlavoredItems.ALFREDO_PASTA);
                        output.accept(FlavoredItems.BOLOGNESE_PASTA);


                        //Placeables

                        //Drinks

                        output.accept(FlavoredItems.APPLE_JUICE);
                        output.accept(FlavoredItems.SWEET_BERRY_JUICE);
                        output.accept(FlavoredItems.GLOW_BERRY_JUICE);
                        output.accept(FlavoredItems.CIDER);
                        output.accept(FlavoredItems.SWEET_BERRY_WINE);
                        output.accept(FlavoredItems.GLOW_BERRY_WINE);
                        output.accept(FlavoredItems.MEAD);


                    }).build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
