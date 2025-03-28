package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
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


    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}