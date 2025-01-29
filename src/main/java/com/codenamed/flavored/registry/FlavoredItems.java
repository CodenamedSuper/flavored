package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import com.codenamed.flavored.item.BeverageItem;
import com.codenamed.flavored.item.JuiceItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
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

    public static final DeferredItem<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    // Drinks

    public static final DeferredItem<Item> SWEET_BERRY_JUICE = ITEMS.register("sweet_berry_juice",
            () -> new JuiceItem(new Item.Properties().food(FlavoredFoods.SWEET_BERRY_JUICE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> GLOW_BERRY_JUICE = ITEMS.register("glow_berry_juice",
            () -> new JuiceItem(new Item.Properties().food(FlavoredFoods.GLOW_BERRY_JUICE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.register("apple_juice",
            () -> new JuiceItem(new Item.Properties().food(FlavoredFoods.APPLE_JUICE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> SWEET_BERRY_WINE = ITEMS.register("sweet_berry_wine",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.SWEET_BERRY_WINE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> GLOW_BERRY_WINE = ITEMS.register("glow_berry_wine",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.GLOW_BERRY_WINE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> CIDER = ITEMS.register("cider",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> MEAD = ITEMS.register("mead",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.MEAD).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
