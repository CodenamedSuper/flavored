package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flavored.MOD_ID);

    public static final DeferredItem<Item> FLOUR = ITEMS.register("flour", () ->
            new Item(new Item.Properties()));

    public static final DeferredItem<Item> DOUGH = ITEMS.register("dough", () ->
            new Item(new Item.Properties()));

    public static final DeferredItem<Item> BATTER = ITEMS.register("batter", () ->
            new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties().food(FlavoredFoods.TOMATO)));

    public static final DeferredItem<Item> GARLIC = ITEMS.register("garlic",
            () -> new ItemNameBlockItem(FlavoredBlocks.GARLICS.get(), new Item.Properties().food(FlavoredFoods.GARLIC)));

    public static final DeferredItem<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.TOMATO_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHEESE_SLICE)));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}