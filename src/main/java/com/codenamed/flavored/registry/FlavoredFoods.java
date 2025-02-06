package com.codenamed.flavored.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class FlavoredFoods {

    // Crops

    public static FoodProperties TOMATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties PEPPER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties CUCUMBER = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static FoodProperties GARLIC = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();

    // Enhanced

    // Dried

    public static FoodProperties DRIED_PEPPER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();

    // Fermented

    public static FoodProperties PICKLED_CUCUMBER = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static FoodProperties PICKLED_BEETROOT = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();
    public static FoodProperties PICKLED_EGG = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();

    // Dishes

    public static FoodProperties TOMATO_SAUCE_PASTA = new FoodProperties.Builder().nutrition(7).saturationModifier(0.6f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties BOLOGNESE_PASTA = new FoodProperties.Builder().nutrition(10).saturationModifier(0.9f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties ALFREDO_PASTA = new FoodProperties.Builder().nutrition(8).saturationModifier(0.6f).usingConvertsTo(Items.BOWL).build();



    // Ingredients

    public static FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();

    public static FoodProperties CHEESE_SLICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();

    // Juices

    public static FoodProperties SWEET_BERRY_JUICE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build();
    public static FoodProperties GLOW_BERRY_JUICE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build();
    public static FoodProperties APPLE_JUICE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build();

    // Beverages

    public static FoodProperties SWEET_BERRY_WINE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).build();
    public static FoodProperties GLOW_BERRY_WINE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).build();
    public static FoodProperties CIDER = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).build();
    public static FoodProperties MEAD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).build();

}
