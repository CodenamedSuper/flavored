package com.sidden.flavored.registry;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;

public class FlavoredRecipeCategories {
    public static RecipeBookCategories FLAVORED_KEG_SEARCH = RecipeBookCategories.valueOf("FLAVORED_KEG_SEARCH");
    public static RecipeBookCategories FLAVORED_KEG = RecipeBookCategories.valueOf("FLAVORED_KEG");

    public static RecipeBookCategories FLAVORED_MIXING_BOWL_SEARCH = RecipeBookCategories.valueOf("FLAVORED_MIXING_BOWL_SEARCH");
    public static RecipeBookCategories FLAVORED_MIXING_BOWL = RecipeBookCategories.valueOf("FLAVORED_MIXING_BOWL");

    public static RecipeBookCategories FLAVORED_OVEN_SEARCH = RecipeBookCategories.valueOf("FLAVORED_OVEN_SEARCH");
    public static RecipeBookCategories FLAVORED_OVEN = RecipeBookCategories.valueOf("FLAVORED_OVEN");

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        event.registerAggregateCategory(FLAVORED_KEG_SEARCH, List.of(FLAVORED_KEG));
        event.registerBookCategories(RecipeBookType.valueOf("FLAVORED_KEG"), List.of(FLAVORED_KEG_SEARCH, FLAVORED_KEG));
        event.registerRecipeCategoryFinder(FlavoredRecipeTypes.KEG_TYPE.get(), recipeHolder -> FLAVORED_KEG);

        event.registerAggregateCategory(FLAVORED_MIXING_BOWL_SEARCH, List.of(FLAVORED_MIXING_BOWL));
        event.registerBookCategories(RecipeBookType.valueOf("FLAVORED_MIXING_BOWL"), List.of(FLAVORED_MIXING_BOWL_SEARCH, FLAVORED_MIXING_BOWL));
        event.registerRecipeCategoryFinder(FlavoredRecipeTypes.MIXING_BOWL_TYPE.get(), recipeHolder -> FLAVORED_MIXING_BOWL);

      event.registerAggregateCategory(FLAVORED_OVEN_SEARCH, List.of(FLAVORED_OVEN));
        event.registerBookCategories(RecipeBookType.valueOf("FLAVORED_OVEN"), List.of(FLAVORED_OVEN_SEARCH, FLAVORED_OVEN));
        event.registerRecipeCategoryFinder(FlavoredRecipeTypes.OVEN_TYPE.get(), recipeHolder -> FLAVORED_OVEN);
    }
}
