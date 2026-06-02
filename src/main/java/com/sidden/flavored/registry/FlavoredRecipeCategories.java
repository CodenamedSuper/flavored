package com.sidden.flavored.registry;

import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;

public class FlavoredRecipeCategories {
    public static RecipeBookCategories FLAVORED_KEG_SEARCH = RecipeBookCategories.valueOf("FLAVORED_KEG_SEARCH");
    public static RecipeBookCategories FLAVORED_KEG = RecipeBookCategories.valueOf("FLAVORED_KEG");

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        event.registerAggregateCategory(FLAVORED_KEG_SEARCH, List.of(FLAVORED_KEG));
        event.registerBookCategories(RecipeBookType.valueOf("FLAVORED_KEG"), List.of(FLAVORED_KEG_SEARCH, FLAVORED_KEG));
        event.registerRecipeCategoryFinder(FlavoredRecipeTypes.KEG_TYPE.get(), recipeHolder -> FLAVORED_KEG);
    }
}
