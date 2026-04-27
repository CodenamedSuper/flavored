package com.sidden.flavored.datagen;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredItemTags;
import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class FlavoredRecipeProvider extends RecipeProvider {
    public FlavoredRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private static ResourceLocation crafting(String path) {
        return ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "crafting/" + path);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 2).requires(Items.COCOA_BEANS).requires(Items.COCOA_BEANS).requires(Items.COCOA_BEANS).requires(Items.SUGAR).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(Items.COCOA_BEANS), has(Items.COCOA_BEANS)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHOCOLATE)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 9).requires(FlavoredBlocks.CHOCOLATE_BLOCK).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_BLOCK), has(FlavoredBlocks.CHOCOLATE_BLOCK)).save(recipeOutput, crafting("chocolate_from_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 3).requires(FlavoredItems.CHOCOLATE_EGG).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(FlavoredItems.CHOCOLATE_EGG), has(FlavoredItems.CHOCOLATE_EGG)).save(recipeOutput, crafting("chocolate_from_egg"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_BLOCK).define('#', FlavoredItems.CHOCOLATE).pattern("###").pattern("###").pattern("###").unlockedBy(getHasName(FlavoredItems.CHOCOLATE), has(FlavoredItems.CHOCOLATE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_BLOCK)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredBlocks.SOFT_CHEESE).requires(Items.MILK_BUCKET).requires(Items.RED_MUSHROOM).requires(Items.BROWN_MUSHROOM).group(getItemName(FlavoredBlocks.SOFT_CHEESE)).unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.SOFT_CHEESE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.SOFT_CHEESE).define('#', FlavoredItems.SOFT_CHEESE_SLICE).pattern("##").pattern("##").group(getItemName(FlavoredBlocks.SOFT_CHEESE)).unlockedBy(getHasName(FlavoredItems.SOFT_CHEESE_SLICE), has(FlavoredItems.SOFT_CHEESE_SLICE)).save(recipeOutput, crafting("soft_cheese_from_slices"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.AGED_CHEESE).define('#', FlavoredItems.AGED_CHEESE_SLICE).pattern("##").pattern("##").unlockedBy(getHasName(FlavoredItems.AGED_CHEESE_SLICE), has(FlavoredItems.AGED_CHEESE_SLICE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.AGED_CHEESE)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.TOMATO_SEEDS).requires(FlavoredItemTags.TOMATOES).unlockedBy(getHasName(FlavoredItems.RED_TOMATO), has(FlavoredItemTags.TOMATOES)).save(recipeOutput, crafting(getItemName(FlavoredItems.TOMATO_SEEDS)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.HAMBURGER, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(Items.COOKED_BEEF).requires(FlavoredItems.RED_TOMATO).requires(FlavoredItems.AGED_CHEESE_SLICE).unlockedBy(getHasName(Items.COOKED_BEEF), has(Items.COOKED_BEEF)).save(recipeOutput, crafting(getItemName(FlavoredItems.HAMBURGER)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHEESE_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.BUTTER).requires(FlavoredItems.AGED_CHEESE_SLICE, 2).unlockedBy(getHasName(FlavoredItems.AGED_CHEESE_SLICE), has(FlavoredItems.AGED_CHEESE_SLICE)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHEESE_SANDWICH)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.HAM_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(Items.HONEYCOMB).requires(FlavoredItems.PORK_JOWL, 2).unlockedBy(getHasName(FlavoredItems.PORK_JOWL), has(FlavoredItems.PORK_JOWL)).save(recipeOutput, crafting(getItemName(FlavoredItems.HAM_SANDWICH)));
    }
}