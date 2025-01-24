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

                        output.accept(FlavoredBlocks.CHOCOLATE_BLOCK);
                        output.accept(FlavoredBlocks.FERMENTER);
                        output.accept(FlavoredItems.CHOCOLATE);

                    }).build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
