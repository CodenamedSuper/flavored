package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flavored.MOD_ID);

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHOCOLATE)));

    public static final DeferredItem<Item> PEPPER = ITEMS.register("pepper",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PEPPER)));

    public static final DeferredItem<Item> DRIED_PEPPER = ITEMS.register("dried_pepper",
            () -> new Item(new Item.Properties().food(FlavoredFoods.DRIED_PEPPER)));

    public static final DeferredItem<Item> PEPPER_SEEDS = ITEMS.register("pepper_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.PEPPER_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHEESE_SLICE)));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
