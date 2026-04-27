package com.sidden.flavored.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class FlavoredFoods {

    public static FoodProperties CHOCOLATE = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f).fast().effect(() -> new MobEffectInstance(FlavoredEffects.SUGAR_RUSH, 1200, 0), 1F).build();
    public static FoodProperties CHOCOLATE_EGG = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f).fast().effect(() -> new MobEffectInstance(FlavoredEffects.SUGAR_RUSH, 1200, 0), 1F).build();
    public static FoodProperties RED_TOMATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
    public static FoodProperties YELLOW_TOMATO = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties GREEN_TOMATO = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static FoodProperties GARLIC = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 1F).build();
    public static FoodProperties SOFT_CHEESE_SLICE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible().build();
    public static FoodProperties AGED_CHEESE_SLICE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5f).alwaysEdible().build();
    public static FoodProperties CORN = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible().build();
    public static FoodProperties GROUND_BEEF = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties COOKED_GROUND_BEEF = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).build();
    public static FoodProperties CHICKEN_DRUMSTICK = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 0), 0.3F).build();
    public static FoodProperties COOKED_CHICKEN_DRUMSTICK = new FoodProperties.Builder().nutrition(3).saturationModifier(0.7f).build();
    public static FoodProperties MUTTON_SHANK = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties COOKED_MUTTON_SHANK = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).build();
    public static FoodProperties PORK_JOWL = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
    public static FoodProperties COOKED_PORK_JOWL = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).build();
    public static FoodProperties PEPPER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).effect(() -> new MobEffectInstance(FlavoredEffects.HEAT, 300, 0), 1F).build();
    public static FoodProperties DRIED_PEPPER = new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).effect(() -> new MobEffectInstance(FlavoredEffects.HEAT, 600, 0), 1F).build();
    public static FoodProperties SPINACH = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build();
    public static FoodProperties COOKIE_DOUGH = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 0), 0.3F).build();
    public static FoodProperties PIZZA_SLICE = new FoodProperties.Builder().nutrition(7).saturationModifier(0.9f).build();
    public static FoodProperties GRILLED_CORN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).build();
    public static FoodProperties HAMBURGER = new FoodProperties.Builder().nutrition(10).saturationModifier(0.9f).build();
    public static FoodProperties CHEESE_SANDWICH = new FoodProperties.Builder().nutrition(7).saturationModifier(0.9f).build();
    public static FoodProperties HAM_SANDWICH = new FoodProperties.Builder().nutrition(7).saturationModifier(0.9f).build();
    public static FoodProperties GARLIC_BREAD = new FoodProperties.Builder().nutrition(8).saturationModifier(0.7f).build();
    public static FoodProperties BUTTER_PASTRY = new FoodProperties.Builder().nutrition(6).saturationModifier(0.7f).build();
    public static FoodProperties CINNAMON_PASTRY = new FoodProperties.Builder().nutrition(6).saturationModifier(0.7f).build();
    public static FoodProperties HONEY_PASTRY = new FoodProperties.Builder().nutrition(6).saturationModifier(0.7f).build();
    public static FoodProperties CHOCOLATE_PASTRY = new FoodProperties.Builder().nutrition(6).saturationModifier(0.7f).effect(() -> new MobEffectInstance(FlavoredEffects.SUGAR_RUSH, 1200, 0), 1F).build();
    public static FoodProperties SALAD = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties CEREAL = new FoodProperties.Builder().nutrition(7).saturationModifier(0.1f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties TOMATO_PASTA = new FoodProperties.Builder().nutrition(8).saturationModifier(0.7f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties PESTO_PASTA = new FoodProperties.Builder().nutrition(8).saturationModifier(0.7f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties CARBONARA_PASTA = new FoodProperties.Builder().nutrition(9).saturationModifier(0.9f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties CREAM_PASTA = new FoodProperties.Builder().nutrition(9).saturationModifier(0.7f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties RAGU_PASTA = new FoodProperties.Builder().nutrition(10).saturationModifier(0.9f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties PORRIDGE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties POLENTA = new FoodProperties.Builder().nutrition(7).saturationModifier(0.4f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties OSSOBUCO = new FoodProperties.Builder().nutrition(9).saturationModifier(0.9f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties SHAKSHOUKA = new FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).usingConvertsTo(Items.BOWL).build();
    public static FoodProperties SWEET_BERRY_JUICE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static FoodProperties GLOW_BERRY_JUICE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 300, 0), 1F).build();
    public static FoodProperties WORT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static FoodProperties APPLE_JUICE = new FoodProperties.Builder().nutrition(5).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static FoodProperties SWEET_BERRY_WINE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static FoodProperties GLOW_BERRY_WINE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE).effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200, 0), 1F).build();
    public static FoodProperties BEER = new FoodProperties.Builder().nutrition(5).saturationModifier(0.3f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static FoodProperties CIDER = new FoodProperties.Builder().nutrition(7).saturationModifier(0.5f).usingConvertsTo(Items.GLASS_BOTTLE).build();


}
