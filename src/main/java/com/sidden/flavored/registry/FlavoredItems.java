package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.item.TomatoItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flavored.MOD_ID);

    public static final DeferredItem<Item> GREEN_TOMATO = ITEMS.register("green_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.GREEN_TOMATO)));

    public static final DeferredItem<Item> YELLOW_TOMATO = ITEMS.register("yellow_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.YELLOW_TOMATO)));

    public static final DeferredItem<Item> RED_TOMATO = ITEMS.register("red_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.RED_TOMATO)));

    public static final DeferredItem<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.TOMATO_BUSH.get(), new Item.Properties()));



    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
