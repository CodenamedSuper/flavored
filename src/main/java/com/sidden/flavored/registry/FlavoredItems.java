package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.item.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Flavored.MOD_ID);

    // Ingredients

    public static final DeferredItem<Item> FLOUR = ITEMS.register("flour",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DOUGH = ITEMS.register("dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BATTER = ITEMS.register("batter",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SALT = ITEMS.register("salt",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHOCOLATE)));

    public static final DeferredItem<Item> SOFT_CHEESE_SLICE = ITEMS.register("soft_cheese_slice",
            () -> new SoftCheeseSliceItem(new Item.Properties().food(FlavoredFoods.SOFT_CHEESE_SLICE)));

    public static final DeferredItem<Item> AGED_CHEESE_SLICE = ITEMS.register("aged_cheese_slice",
            () -> new CheesyItem(new Item.Properties().food(FlavoredFoods.AGED_CHEESE_SLICE)));


    // Crops

    public static final DeferredItem<Item> GREEN_TOMATO = ITEMS.register("green_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.GREEN_TOMATO)));

    public static final DeferredItem<Item> YELLOW_TOMATO = ITEMS.register("yellow_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.YELLOW_TOMATO)));

    public static final DeferredItem<Item> RED_TOMATO = ITEMS.register("red_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.RED_TOMATO)));

    public static final DeferredItem<Item> CUCUMBER = ITEMS.register("cucumber",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CUCUMBER)));

    public static final DeferredItem<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CORN)));

    public static final DeferredItem<Item> CINNAMON = ITEMS.register("cinnamon",
            () -> new Item(new Item.Properties()));

    // Seeds

    public static final DeferredItem<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.TOMATO_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.CUCUMBER_VINE.get(), new Item.Properties()));


    public static final DeferredItem<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.CORN_BUSH.get(), new  Item.Properties()));


    // Raw Cuts

    public static final DeferredItem<Item> GROUND_BEEF = ITEMS.register("ground_beef",
            () -> new Item(new Item.Properties().food(FlavoredFoods.GROUND_BEEF)));

    public static final DeferredItem<Item> CHICKEN_DRUMSTICK = ITEMS.register("chicken_drumstick",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHICKEN_DRUMSTICK)));

    public static final DeferredItem<Item> MUTTON_SHANK = ITEMS.register("mutton_shank",
            () -> new Item(new Item.Properties().food(FlavoredFoods.MUTTON_SHANK)));

    public static final DeferredItem<Item> PORK_JOWL = ITEMS.register("pork_jowl",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PORK_JOWL)));

    // Cooked Cuts

    public static final DeferredItem<Item> COOKED_GROUND_BEEF = ITEMS.register("cooked_ground_beef",
            () -> new Item(new Item.Properties().food(FlavoredFoods.COOKED_GROUND_BEEF)));


    public static final DeferredItem<Item> COOKED_CHICKEN_DRUMSTICK = ITEMS.register("cooked_chicken_drumstick",
            () -> new Item(new Item.Properties().food(FlavoredFoods.COOKED_CHICKEN_DRUMSTICK)));


    public static final DeferredItem<Item> COOKED_MUTTON_SHANK = ITEMS.register("cooked_mutton_shank",
            () -> new Item(new Item.Properties().food(FlavoredFoods.COOKED_MUTTON_SHANK)));


    public static final DeferredItem<Item> COOKED_PORK_JOWL = ITEMS.register("cooked_pork_jowl",
            () -> new Item(new Item.Properties().food(FlavoredFoods.COOKED_PORK_JOWL)));

    // Foodstuffs

    // Pickles

    public static final DeferredItem<Item> PICKLE = ITEMS.register("pickle",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PICKLE)));

    // Drinks

    // Beverages

    // Tools

    public static final DeferredItem<Item> KNIFE = ITEMS.register("knife", () ->
            new KnifeItem((new Item.Properties()).durability(100).component(DataComponents.TOOL, KnifeItem.createToolProperties()).attributes(KnifeItem.createAttributes())));

    public static final DeferredItem<Item> WHISK = ITEMS.register("whisk", () ->
            new WhiskItem((new Item.Properties()).durability(100).component(DataComponents.TOOL, WhiskItem.createToolProperties())));


    // Spawn Eggs

    public static final DeferredItem<Item> CHOCKEN_SPAWN_EGG = ITEMS.register("chocken_spawn_egg",
            () -> new DeferredSpawnEggItem(FlavoredEntities.CHOCKEN, 0x8f4e30, 0xdca37a,new Item.Properties()));

    // Other

    public static final DeferredItem<Item> CHOCOLATE_EGG = ITEMS.register("chocolate_egg",
            () -> new ChocolateEggItem(new Item.Properties().stacksTo(16).food(FlavoredFoods.CHOCOLATE_EGG)));

    public static final DeferredItem<Item> CURED_APPLE = ITEMS.register("cured_apple",
            () -> new Item(new Item.Properties()));



    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
