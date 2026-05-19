package com.sidden.flavored.datagen;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredItemTags;
import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 4).requires(FlavoredBlocks.CHOCOLATE_BLOCK).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_BLOCK), has(FlavoredBlocks.CHOCOLATE_BLOCK)).save(recipeOutput, crafting("chocolate_from_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 1).requires(FlavoredItems.CHOCOLATE_EGG).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(FlavoredItems.CHOCOLATE_EGG), has(FlavoredItems.CHOCOLATE_EGG)).save(recipeOutput, crafting("chocolate_from_egg"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_BLOCK).define('#', FlavoredItems.CHOCOLATE).pattern("##").pattern("##").unlockedBy(getHasName(FlavoredItems.CHOCOLATE), has(FlavoredItems.CHOCOLATE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_BLOCK)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_TILES, 4).define('#', FlavoredBlocks.CHOCOLATE_BLOCK).pattern("##").pattern("##").group(getItemName(FlavoredBlocks.CHOCOLATE_BLOCK)).unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_BLOCK), has(FlavoredBlocks.CHOCOLATE_BLOCK)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_TILES)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_TILE_STAIRS, 4).define('#', FlavoredBlocks.CHOCOLATE_TILES).pattern("#  ").pattern("## ").pattern("###").unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_TILES), has(FlavoredBlocks.CHOCOLATE_TILES)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_TILE_STAIRS)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_TILE_SLAB, 6).define('#', FlavoredBlocks.CHOCOLATE_TILES).pattern("###").unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_TILES), has(FlavoredBlocks.CHOCOLATE_TILES)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_TILE_SLAB)));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.SOFT_CHEESE).define('#', FlavoredItems.SOFT_CHEESE_SLICE).pattern("##").pattern("##").group(getItemName(FlavoredBlocks.SOFT_CHEESE)).unlockedBy(getHasName(FlavoredItems.SOFT_CHEESE_SLICE), has(FlavoredItems.SOFT_CHEESE_SLICE)).save(recipeOutput, crafting("soft_cheese_from_slices"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.AGED_CHEESE).define('#', FlavoredItems.AGED_CHEESE_SLICE).pattern("##").pattern("##").unlockedBy(getHasName(FlavoredItems.AGED_CHEESE_SLICE), has(FlavoredItems.AGED_CHEESE_SLICE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.AGED_CHEESE)));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.PIZZA).define('#', FlavoredItems.PIZZA_SLICE).pattern("##").pattern("##").group(getItemName(FlavoredItems.PIZZA_SLICE)).unlockedBy(getHasName(FlavoredItems.PIZZA_SLICE), has(FlavoredItems.PIZZA_SLICE)).save(recipeOutput, crafting("pizza_from_slices"));


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FlavoredBlocks.OVEN).define('#', Blocks.COBBLED_DEEPSLATE).pattern("###").define('X', Blocks.PACKED_MUD).pattern("X X").pattern("XXX").unlockedBy(getHasName(Blocks.COBBLED_DEEPSLATE), has(Blocks.COBBLED_DEEPSLATE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.OVEN)));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FlavoredBlocks.KEG).define('#', ItemTags.PLANKS).define('X', Items.COPPER_INGOT).pattern("X#X").pattern("X X").pattern("X#X").unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.KEG)));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FlavoredBlocks.MIXING_BOWL).define('#', Items.IRON_INGOT).define('X', Items.IRON_NUGGET).pattern("X X").pattern("###").unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.MIXING_BOWL)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.FLOUR).requires(Items.WHEAT).unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(recipeOutput, crafting(getItemName(FlavoredItems.FLOUR)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.BUTTER).requires(Items.MILK_BUCKET).unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET)).save(recipeOutput, crafting(getItemName(FlavoredItems.BUTTER)));


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.TOMATO_SEEDS).requires(FlavoredItemTags.TOMATOES).unlockedBy(getHasName(FlavoredItems.RED_TOMATO), has(FlavoredItemTags.TOMATOES)).save(recipeOutput, crafting(getItemName(FlavoredItems.TOMATO_SEEDS)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.SPINACH_SEEDS).requires(FlavoredItems.SPINACH).unlockedBy(getHasName(FlavoredItems.SPINACH), has(FlavoredItems.SPINACH)).save(recipeOutput, crafting(getItemName(FlavoredItems.SPINACH_SEEDS)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.CORN_SEEDS).requires(FlavoredItems.CORN).unlockedBy(getHasName(FlavoredItems.CORN), has(FlavoredItems.CORN)).save(recipeOutput, crafting(getItemName(FlavoredItems.CORN_SEEDS)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.PEPPER_SEEDS).requires(FlavoredItems.PEPPER).unlockedBy(getHasName(FlavoredItems.PEPPER), has(FlavoredItems.PEPPER)).save(recipeOutput, crafting(getItemName(FlavoredItems.PEPPER_SEEDS)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.HAMBURGER, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(Items.COOKED_BEEF).requires(FlavoredItems.RED_TOMATO).requires(FlavoredItems.AGED_CHEESE_SLICE).unlockedBy(getHasName(Items.COOKED_BEEF), has(Items.COOKED_BEEF)).save(recipeOutput, crafting(getItemName(FlavoredItems.HAMBURGER)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHEESE_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.BUTTER).requires(FlavoredItems.AGED_CHEESE_SLICE, 2).unlockedBy(getHasName(FlavoredItems.AGED_CHEESE_SLICE), has(FlavoredItems.AGED_CHEESE_SLICE)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHEESE_SANDWICH)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.HAM_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(Items.HONEYCOMB).requires(FlavoredItems.PORK_JOWL, 2).unlockedBy(getHasName(FlavoredItems.COOKED_PORK_JOWL), has(FlavoredItems.COOKED_PORK_JOWL)).save(recipeOutput, crafting(getItemName(FlavoredItems.HAM_SANDWICH)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHICKEN_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(FlavoredItems.YELLOW_TOMATO).requires(FlavoredItems.COOKED_CHICKEN_DRUMSTICK, 2).unlockedBy(getHasName(FlavoredItems.COOKED_CHICKEN_DRUMSTICK), has(FlavoredItems.COOKED_CHICKEN_DRUMSTICK)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHICKEN_SANDWICH)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.SHAWARMA, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(FlavoredItems.RED_TOMATO).requires(FlavoredItems.GARLIC).requires(FlavoredItems.COOKED_MUTTON_SHANK, 2).unlockedBy(getHasName(FlavoredItems.COOKED_MUTTON_SHANK), has(FlavoredItems.COOKED_MUTTON_SHANK)).save(recipeOutput, crafting(getItemName(FlavoredItems.SHAWARMA)));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.SWEET_BERRY_JUICE).define('#', Items.GLASS_BOTTLE).define('X', Items.SWEET_BERRIES).pattern(" X ").pattern("X#X").unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES)).save(recipeOutput, crafting(getItemName(FlavoredItems.SWEET_BERRY_JUICE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.GLOW_BERRY_JUICE).define('#', Items.GLASS_BOTTLE).define('X', Items.GLOW_BERRIES).pattern(" X ").pattern("X#X").unlockedBy(getHasName(Items.GLOW_BERRIES), has(Items.GLOW_BERRIES)).save(recipeOutput, crafting(getItemName(FlavoredItems.GLOW_BERRY_JUICE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.WORT).define('#', Items.GLASS_BOTTLE).define('X', Items.WHEAT).pattern(" X ").pattern("X#X").unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(recipeOutput, crafting(getItemName(FlavoredItems.WORT)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.APPLE_JUICE).define('#', Items.GLASS_BOTTLE).define('X', Items.APPLE).pattern("X#X").unlockedBy(getHasName(Items.APPLE), has(Items.APPLE)).save(recipeOutput, crafting(getItemName(FlavoredItems.APPLE_JUICE)));


    }
}