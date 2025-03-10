package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flavored.MOD_ID);

//    public static final DeferredItem<Item> TEMPLATE = ITEMS.register("template", () ->
//            new Item(new Item.Properties()));




    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}