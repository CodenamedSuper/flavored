package com.sidden.flavored.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class FlavoredFoods {

    public static FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f).fast().effect(() -> new MobEffectInstance(FlavoredEffects.SUGAR_RUSH, 1200, 0), 1F).build();
    public static FoodProperties CHOCOLATE_EGG = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f).fast().effect(() -> new MobEffectInstance(FlavoredEffects.SUGAR_RUSH, 1200, 0), 1F).build();
    public static FoodProperties RED_TOMATO = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static FoodProperties YELLOW_TOMATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties GREEN_TOMATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties SOFT_CHEESE_SLICE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible().build();
    public static FoodProperties AGED_CHEESE_SLICE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5f).alwaysEdible().build();
    public static FoodProperties CUCUMBER = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).alwaysEdible().build();

}
