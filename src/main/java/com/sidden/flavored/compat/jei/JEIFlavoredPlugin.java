package com.sidden.flavored.compat.jei;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.client.screen.KegScreen;
import com.sidden.flavored.client.screen.MixingBowlScreen;
import com.sidden.flavored.client.screen.OvenScreen;
import com.sidden.flavored.recipe.BakingRecipe;
import com.sidden.flavored.recipe.FermentingRecipe;
import com.sidden.flavored.recipe.MixingRecipe;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIFlavoredPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FermentingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MixingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BakingRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<FermentingRecipe> fermentingRecipes = recipeManager
                .getAllRecipesFor(FlavoredRecipeTypes.KEG_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(FermentingRecipeCategory.FERMENTING_RECIPE_RECIPE_TYPE, fermentingRecipes);

        List<MixingRecipe> mixingRecipes = recipeManager
                .getAllRecipesFor(FlavoredRecipeTypes.MIXING_BOWL_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MixingRecipeCategory.MIXING_RECIPE_RECIPE_TYPE, mixingRecipes);
        List<BakingRecipe> bakingRecipes = recipeManager
                .getAllRecipesFor(FlavoredRecipeTypes.OVEN_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(BakingRecipeCategory.BAKING_RECIPE_RECIPE_TYPE, bakingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(KegScreen.class, 74, 30, 22, 20,
                FermentingRecipeCategory.FERMENTING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(MixingBowlScreen.class, 74, 30, 22, 20,
                MixingRecipeCategory.MIXING_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(OvenScreen.class, 74, 30, 22, 20,
                BakingRecipeCategory.BAKING_RECIPE_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(FlavoredBlocks.KEG.get().asItem()),
                FermentingRecipeCategory.FERMENTING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(FlavoredBlocks.MIXING_BOWL.get().asItem()),
                MixingRecipeCategory.MIXING_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(FlavoredBlocks.OVEN.get().asItem()),
                BakingRecipeCategory.BAKING_RECIPE_RECIPE_TYPE);
    }
}