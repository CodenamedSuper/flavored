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

                        output.accept(FlavoredBlocks.ROCK_SALT);
                        output.accept(FlavoredBlocks.BUDDING_ROCK_SALT);
                        output.accept(FlavoredBlocks.SALT_BLOCK);
                        output.accept(FlavoredBlocks.SALT_CLUSTER);
                        output.accept(FlavoredBlocks.SALT_LAMP);
                        output.accept(FlavoredItems.SALT);
                        output.accept(FlavoredBlocks.CHOCOLATE_BLOCK);
                        output.accept(FlavoredBlocks.FERMENTER);
                        output.accept(FlavoredItems.CHOCOLATE);
                        output.accept(FlavoredItems.PEPPER);
                        output.accept(FlavoredItems.DRIED_PEPPER);
                        output.accept(FlavoredItems.PEPPER_SEEDS);
                        output.accept(FlavoredBlocks.WILD_PEPPER);
                        output.accept(FlavoredBlocks.RAW_CHEESE);
                        output.accept(FlavoredBlocks.CHEESE);
                        output.accept(FlavoredItems.CHEESE_SLICE);
                        output.accept(FlavoredItems.SWEET_BERRY_JUICE);
                        output.accept(FlavoredItems.GLOW_BERRY_JUICE);
                        output.accept(FlavoredItems.APPLE_JUICE);
                        output.accept(FlavoredItems.SWEET_BERRY_WINE);
                        output.accept(FlavoredItems.GLOW_BERRY_WINE);
                        output.accept(FlavoredItems.CIDER);
                        output.accept(FlavoredItems.MEAD);
                        output.accept(FlavoredBlocks.DOUGH);

                    }).build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
