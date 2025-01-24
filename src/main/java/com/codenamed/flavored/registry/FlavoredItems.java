package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flavored.MOD_ID);

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHOCOLATE)));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
