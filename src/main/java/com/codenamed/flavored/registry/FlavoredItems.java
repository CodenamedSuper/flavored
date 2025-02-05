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


    // Crops

    public static final DeferredItem<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties().food(FlavoredFoods.TOMATO)));

    public static final DeferredItem<Item> PEPPER = ITEMS.register("pepper",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PEPPER)));

    public static final DeferredItem<Item> CUCUMBER = ITEMS.register("cucumber",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CUCUMBER)));

    public static final DeferredItem<Item> GARLIC = ITEMS.register("garlic",
            () -> new ItemNameBlockItem(FlavoredBlocks.GARLICS.get(), new Item.Properties().food(FlavoredFoods.GARLIC)));

    // Enhanced Foods

    // Dried

    public static final DeferredItem<Item> DRIED_PEPPER = ITEMS.register("dried_pepper",
            () -> new Item(new Item.Properties().food(FlavoredFoods.DRIED_PEPPER)));

    // Fermented

    public static final DeferredItem<Item> PICKLED_CUCUMBER = ITEMS.register("pickled_cucumber",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PICKLED_CUCUMBER)));

    public static final DeferredItem<Item> PICKLED_BEETROOT = ITEMS.register("pickled_beetroot",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PICKLED_BEETROOT)));

    public static final DeferredItem<Item> PICKLED_EGG = ITEMS.register("pickled_egg",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PICKLED_EGG)));

    // Ingredients

    public static final DeferredItem<Item> CHEESE_SLICE = ITEMS.register("cheese_slice",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHEESE_SLICE)));

    public static final DeferredItem<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHOCOLATE)));

    // Seeds

    public static final DeferredItem<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.TOMATO_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> PEPPER_SEEDS = ITEMS.register("pepper_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.PEPPER_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.CUCUMBER_BUSH.get(), new Item.Properties()));

    // Juices

    public static final DeferredItem<Item> SWEET_BERRY_JUICE = ITEMS.register("sweet_berry_juice",
            () -> new JuiceItem(new Item.Properties().food(FlavoredFoods.SWEET_BERRY_JUICE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> GLOW_BERRY_JUICE = ITEMS.register("glow_berry_juice",
            () -> new JuiceItem(new Item.Properties().food(FlavoredFoods.GLOW_BERRY_JUICE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.register("apple_juice",
            () -> new JuiceItem(new Item.Properties().food(FlavoredFoods.APPLE_JUICE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    // Beverages

    public static final DeferredItem<Item> SWEET_BERRY_WINE = ITEMS.register("sweet_berry_wine",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.SWEET_BERRY_WINE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> GLOW_BERRY_WINE = ITEMS.register("glow_berry_wine",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.GLOW_BERRY_WINE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> CIDER = ITEMS.register("cider",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final DeferredItem<Item> MEAD = ITEMS.register("mead",
            () -> new BeverageItem(new Item.Properties().food(FlavoredFoods.MEAD).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    // Misc


    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
