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

    public static final DeferredItem<Item> PASTRY_DOUGH = ITEMS.register("pastry_dough",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> COOKIE_DOUGH = ITEMS.register("cookie_dough",
            () -> new Item(new Item.Properties().food(FlavoredFoods.COOKIE_DOUGH)));

    public static final DeferredItem<Item> BATTER = ITEMS.register("batter",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> PASTA = ITEMS.register("pasta",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BUTTER = ITEMS.register("butter",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHOCOLATE = ITEMS.register("chocolate",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHOCOLATE)));

    public static final DeferredItem<Item> SOFT_CHEESE_SLICE = ITEMS.register("soft_cheese_slice",
            () -> new SoftCheesyItem(new Item.Properties().food(FlavoredFoods.SOFT_CHEESE_SLICE)));

    public static final DeferredItem<Item> AGED_CHEESE_SLICE = ITEMS.register("aged_cheese_slice",
            () -> new AgedCheesyItem(new Item.Properties().food(FlavoredFoods.AGED_CHEESE_SLICE)));


    // Crops

    public static final DeferredItem<Item> GREEN_TOMATO = ITEMS.register("green_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.GREEN_TOMATO)));

    public static final DeferredItem<Item> YELLOW_TOMATO = ITEMS.register("yellow_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.YELLOW_TOMATO)));

    public static final DeferredItem<Item> RED_TOMATO = ITEMS.register("red_tomato",
            () -> new TomatoItem(new Item.Properties().food(FlavoredFoods.RED_TOMATO)));

    public static final DeferredItem<Item> CORN = ITEMS.register("corn",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CORN)));

    public static final DeferredItem<Item> GARLIC = ITEMS.register("garlic",
            () -> new ItemNameBlockItem(FlavoredBlocks.GARLICS.get(), new Item.Properties().food(FlavoredFoods.GARLIC)));

    public static final DeferredItem<Item> PEPPER = ITEMS.register("pepper",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PEPPER)));

    public static final DeferredItem<Item> SPINACH = ITEMS.register("spinach",
            () -> new Item(new Item.Properties().food(FlavoredFoods.SPINACH)));

    public static final DeferredItem<Item> CINNAMON = ITEMS.register("cinnamon",
            () -> new Item(new Item.Properties()));

    // Seeds

    public static final DeferredItem<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.TOMATO_BUSH.get(), new Item.Properties()));

    public static final DeferredItem<Item> CORN_SEEDS = ITEMS.register("corn_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.CORN_BUSH.get(), new  Item.Properties()));

    public static final DeferredItem<Item> PEPPER_SEEDS = ITEMS.register("pepper_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.PEPPER_BUSH.get(), new  Item.Properties()));

    public static final DeferredItem<Item> SPINACH_SEEDS = ITEMS.register("spinach_seeds",
            () -> new ItemNameBlockItem(FlavoredBlocks.SPINACH_BUSH.get(), new  Item.Properties()));


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

    // Normal Foodstuffs

    public static final DeferredItem<Item> PIZZA_SLICE = ITEMS.register("pizza_slice",
            () -> new SoftCheesyItem(new Item.Properties().food(FlavoredFoods.PIZZA_SLICE)));

    public static final DeferredItem<Item> HAMBURGER = ITEMS.register("hamburger",
            () -> new AgedCheesyItem(new Item.Properties().food(FlavoredFoods.HAMBURGER)));

    public static final DeferredItem<Item> HAM_SANDWICH = ITEMS.register("ham_sandwich",
            () -> new Item(new Item.Properties().food(FlavoredFoods.HAM_SANDWICH)));

    public static final DeferredItem<Item> CHICKEN_SANDWICH = ITEMS.register("chicken_sandwich",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHICKEN_SANDWICH)));

    public static final DeferredItem<Item> SHAWARMA = ITEMS.register("shawarma",
            () -> new Item(new Item.Properties().food(FlavoredFoods.SHAWARMA)));

    public static final DeferredItem<Item> CHEESE_SANDWICH = ITEMS.register("cheese_sandwich",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHEESE_SANDWICH)));

    public static final DeferredItem<Item> GRILLED_CORN = ITEMS.register("grilled_corn",
            () -> new Item(new Item.Properties().food(FlavoredFoods.GRILLED_CORN)));

    public static final DeferredItem<Item> BUTTER_PASTRY = ITEMS.register("butter_pastry",
            () -> new Item(new Item.Properties().food(FlavoredFoods.BUTTER_PASTRY)));

    public static final DeferredItem<Item> HONEY_PASTRY = ITEMS.register("honey_pastry",
            () -> new Item(new Item.Properties().food(FlavoredFoods.HONEY_PASTRY)));

    public static final DeferredItem<Item> CHOCOLATE_PASTRY = ITEMS.register("chocolate_pastry",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CHOCOLATE_PASTRY)));

    public static final DeferredItem<Item> CINNAMON_PASTRY = ITEMS.register("cinnamon_pastry",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CINNAMON_PASTRY)));

    public static final DeferredItem<Item> GARLIC_BREAD = ITEMS.register("garlic_bread",
            () -> new Item(new Item.Properties().food(FlavoredFoods.GARLIC_BREAD)));

    // Dishes

    public static final DeferredItem<Item> PORRIDGE = ITEMS.register("porridge",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PORRIDGE).stacksTo(1)));

    public static final DeferredItem<Item> SHAKSHOUKA = ITEMS.register("shakshouka",
            () -> new Item(new Item.Properties().food(FlavoredFoods.SHAKSHOUKA).stacksTo(1)));

    public static final DeferredItem<Item> TOMATO_PASTA = ITEMS.register("tomato_pasta",
            () -> new Item(new Item.Properties().food(FlavoredFoods.TOMATO_PASTA).stacksTo(1)));

    public static final DeferredItem<Item> PESTO_PASTA = ITEMS.register("pesto_pasta",
            () -> new Item(new Item.Properties().food(FlavoredFoods.PESTO_PASTA).stacksTo(1)));

    public static final DeferredItem<Item> CREAM_PASTA = ITEMS.register("cream_pasta",
            () -> new AgedCheesyItem(new Item.Properties().food(FlavoredFoods.CREAM_PASTA).stacksTo(1)));

    public static final DeferredItem<Item> CARBONARA_PASTA = ITEMS.register("carbonara_pasta",
            () -> new AgedCheesyItem(new Item.Properties().food(FlavoredFoods.CARBONARA_PASTA).stacksTo(1)));

    public static final DeferredItem<Item> RAGU_PASTA = ITEMS.register("ragu_pasta",
            () -> new Item(new Item.Properties().food(FlavoredFoods.RAGU_PASTA).stacksTo(1)));

    public static final DeferredItem<Item> OSSOBUCO = ITEMS.register("ossobuco",
            () -> new Item(new Item.Properties().food(FlavoredFoods.OSSOBUCO).stacksTo(1)));

    public static final DeferredItem<Item> SALAD = ITEMS.register("salad",
            () -> new Item(new Item.Properties().food(FlavoredFoods.SALAD).stacksTo(1)));

    public static final DeferredItem<Item> CEREAL = ITEMS.register("cereal",
            () -> new Item(new Item.Properties().food(FlavoredFoods.CEREAL).stacksTo(1)));

    public static final DeferredItem<Item> POLENTA = ITEMS.register("polenta",
            () -> new Item(new Item.Properties().food(FlavoredFoods.POLENTA).stacksTo(1)));

    // Drinks

    public static final DeferredItem<Item> SWEET_BERRY_JUICE = ITEMS.register("sweet_berry_juice",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.SWEET_BERRY_JUICE)));

    public static final DeferredItem<Item> GLOW_BERRY_JUICE = ITEMS.register("glow_berry_juice",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.GLOW_BERRY_JUICE)));

    public static final DeferredItem<Item> WORT = ITEMS.register("wort",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.WORT)));

    public static final DeferredItem<Item> APPLE_JUICE = ITEMS.register("apple_juice",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.APPLE_JUICE)));



    // Beverages

    public static final DeferredItem<Item> SWEET_BERRY_WINE = ITEMS.register("sweet_berry_wine",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.SWEET_BERRY_WINE).stacksTo(1)));

    public static final DeferredItem<Item> GLOW_BERRY_WINE = ITEMS.register("glow_berry_wine",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.GLOW_BERRY_WINE).stacksTo(1)));

    public static final DeferredItem<Item> BEER = ITEMS.register("beer",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.BEER).stacksTo(1)));

    public static final DeferredItem<Item> CIDER = ITEMS.register("cider",
            () -> new DrinkItem(new Item.Properties().food(FlavoredFoods.CIDER).stacksTo(1)));


    // Tools

    public static final DeferredItem<Item> KNIFE = ITEMS.register("knife", () ->
            new KnifeItem((new Item.Properties()).durability(200).component(DataComponents.TOOL, KnifeItem.createToolProperties()).attributes(KnifeItem.createAttributes())));

    public static final DeferredItem<Item> WHISK = ITEMS.register("whisk", () ->
            new WhiskItem((new Item.Properties()).durability(2000).component(DataComponents.TOOL, WhiskItem.createToolProperties())));


    // Spawn Eggs

    public static final DeferredItem<Item> CHOCKEN_SPAWN_EGG = ITEMS.register("chocken_spawn_egg",
            () -> new DeferredSpawnEggItem(FlavoredEntities.CHOCKEN, 0x8f4e30, 0xdca37a,new Item.Properties()));

    // Other

    public static final DeferredItem<Item> CHOCOLATE_EGG = ITEMS.register("chocolate_egg",
            () -> new ChocolateEggItem(new Item.Properties().stacksTo(16).food(FlavoredFoods.CHOCOLATE_EGG)));

    public static final DeferredItem<Item> DRIED_PEPPER = ITEMS.register("dried_pepper",
            () -> new Item(new Item.Properties().food(FlavoredFoods.DRIED_PEPPER)));



    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
