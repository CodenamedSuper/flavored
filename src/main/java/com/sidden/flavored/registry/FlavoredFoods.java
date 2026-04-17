package com.sidden.flavored.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

    public static FoodProperties GROUND_BEEF = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties COOKED_GROUND_BEEF = new FoodProperties.Builder().nutrition(5).saturationModifier(0.9f).build();

    public static FoodProperties CHICKEN_DRUMSTICK = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();
    public static FoodProperties COOKED_CHICKEN_DRUMSTICK = new FoodProperties.Builder().nutrition(3).saturationModifier(0.9f).build();

    public static FoodProperties MUTTON_SHANK = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties COOKED_MUTTON_SHANK = new FoodProperties.Builder().nutrition(5).saturationModifier(0.9f).build();

    public static FoodProperties PORK_JOWL = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties COOKED_PORK_JOWL = new FoodProperties.Builder().nutrition(5).saturationModifier(0.9f).build();

    public static FoodProperties PICKLE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build();


}
