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

    public static final DeferredItem<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.CUCUMBER_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> CUCUMBER = ITEMS.register("cucumber",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CUCUMBER)));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}