package com.codenamed.flavored.registry;

import net.minecraft.world.food.FoodProperties;

public class FlavoredFoods {
    public static FoodProperties TOMATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();

    public static FoodProperties CHEESE_SLICE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();

    public static FoodProperties GARLIC = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();

}
