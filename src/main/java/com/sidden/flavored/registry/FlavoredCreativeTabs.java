package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FlavoredItems.RED_TOMATO.get()))
                    .title(Component.translatable("creativetab.flavored.flavored"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(FlavoredBlocks.ROCK_SALT);
                        output.accept(FlavoredBlocks.BUDDING_ROCK_SALT);
                        output.accept(FlavoredBlocks.SALT_BLOCK);
                        output.accept(FlavoredBlocks.SMALL_SALT_BUD);
                        output.accept(FlavoredBlocks.MEDIUM_SALT_BUD);
                        output.accept(FlavoredBlocks.LARGE_SALT_BUD);
                        output.accept(FlavoredBlocks.SALT_CLUSTER);
                        output.accept(FlavoredBlocks.CHOCOLATE_BLOCK);
                        output.accept(FlavoredItems.GREEN_TOMATO);
                        output.accept(FlavoredItems.YELLOW_TOMATO);
                        output.accept(FlavoredItems.RED_TOMATO);
                        output.accept(FlavoredItems.SALT);
                        output.accept(FlavoredItems.TOMATO_SEEDS);
                        output.accept(FlavoredItems.SOFT_CHEESE_SLICE);
                        output.accept(FlavoredItems.AGED_CHEESE_SLICE);
                        output.accept(FlavoredBlocks.SOFT_CHEESE);
                        output.accept(FlavoredBlocks.AGED_CHEESE);
                        output.accept(FlavoredItems.CHOCOLATE);
                        output.accept(FlavoredItems.CHOCOLATE_EGG);
                        output.accept(FlavoredItems.CHOCKEN_SPAWN_EGG);


                    }).build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
