package com.sidden.flavored.datagen;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.builder.BakeRecipeBuilder;
import com.sidden.flavored.recipe.builder.FermentingRecipeBuilder;
import com.sidden.flavored.recipe.builder.MixingRecipeBuilder;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredItemTags;
import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

    private static ResourceLocation mixing(String path) {
        return ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "mixing/" + path);
    }

    private static ResourceLocation baking(String path) {
        return ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "baking/" + path);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        FermentingRecipeBuilder.fermenting(Ingredient.of(FlavoredItems.WORT), Ingredient.of(Items.SUGAR), RecipeCategory.FOOD, FlavoredItems.BEER)
                .particleColor("c99f4e")
                .unlockedBy("has_wort", has(FlavoredItems.WORT))
                .save(recipeOutput);
        FermentingRecipeBuilder.fermenting(Ingredient.of(FlavoredItems.APPLE_JUICE), Ingredient.of(Items.SUGAR), RecipeCategory.FOOD, FlavoredItems.CIDER)
                .particleColor("da9a37")
                .unlockedBy("has_apple_juice", has(FlavoredItems.APPLE_JUICE))
                .save(recipeOutput);
        FermentingRecipeBuilder.fermenting(Ingredient.of(Items.SPIDER_EYE), Ingredient.of(FlavoredItemTags.FUNGI), RecipeCategory.BREWING, Items.FERMENTED_SPIDER_EYE)
                .particleColor("9d1e2d")
                .unlockedBy("has_spider_eye", has(Items.SPIDER_EYE))
                .save(recipeOutput);
        FermentingRecipeBuilder.fermenting(Ingredient.of(FlavoredItems.GLOW_BERRY_JUICE), Ingredient.of(Items.SUGAR), RecipeCategory.FOOD, FlavoredItems.GLOW_BERRY_WINE)
                .particleColor("ffb034")
                .unlockedBy("has_glow_berry_juice", has(FlavoredItems.GLOW_BERRY_JUICE))
                .save(recipeOutput);
        FermentingRecipeBuilder.fermenting(Ingredient.of(FlavoredItems.SWEET_BERRY_JUICE), Ingredient.of(Items.SUGAR), RecipeCategory.FOOD, FlavoredItems.SWEET_BERRY_WINE)
                .particleColor("bd334a")
                .unlockedBy("has_sweet_berry_juice", has(FlavoredItems.SWEET_BERRY_JUICE))
                .save(recipeOutput);
        FermentingRecipeBuilder.fermenting(Ingredient.of(Items.MILK_BUCKET), Ingredient.of(FlavoredItemTags.FUNGI), RecipeCategory.FOOD, FlavoredBlocks.SOFT_CHEESE)
                .particleColor("f6e5d3")
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .save(recipeOutput);

        new MixingRecipeBuilder(RecipeCategory.MISC, Ingredient.EMPTY, Ingredient.of(Items.WATER_BUCKET), FlavoredItems.DOUGH, 1)
                .requires(Ingredient.of(FlavoredItems.FLOUR), 3)
                .unlockedBy(getItemName(FlavoredItems.FLOUR.get()),
                        has(FlavoredItems.FLOUR))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.DOUGH)));

        new MixingRecipeBuilder(RecipeCategory.MISC, Ingredient.of(Items.BOWL), Ingredient.of(Items.MILK_BUCKET), FlavoredItems.BATTER, 1)
                .requires(Ingredient.of(FlavoredItems.FLOUR), 2)
                .requires(Ingredient.of(Items.EGG))
                .requires(Ingredient.of(Items.SUGAR))
                .unlockedBy(getItemName(FlavoredItems.FLOUR.get()),
                        has(FlavoredItems.FLOUR))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.BATTER)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.CARBONARA_PASTA, 1)
                .requires(Ingredient.of(FlavoredItems.AGED_CHEESE_SLICE))
                .requires(Ingredient.of(Items.EGG), 2)
                .requires(Ingredient.of(FlavoredItems.COOKED_PORK_JOWL))
                .requires(Ingredient.of(FlavoredItems.PASTA))
                .unlockedBy(getItemName(FlavoredItems.PASTA.get()),
                        has(FlavoredItems.PASTA))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.CARBONARA_PASTA)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.CREAM_PASTA, 1)
                .requires(Ingredient.of(FlavoredItems.AGED_CHEESE_SLICE))
                .requires(Ingredient.of(FlavoredItems.BUTTER))
                .requires(Ingredient.of(FlavoredItems.GARLIC))
                .requires(Ingredient.of(FlavoredItems.PASTA))

                .unlockedBy(getItemName(FlavoredItems.PASTA.get()),
                        has(FlavoredItems.PASTA))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.CREAM_PASTA)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.PESTO_PASTA, 1)
                .requires(Ingredient.of(FlavoredItems.SPINACH), 2)
                .requires(Ingredient.of(FlavoredItems.GARLIC))
                .requires(Ingredient.of(FlavoredItems.PASTA))

                .unlockedBy(getItemName(FlavoredItems.PASTA.get()),
                        has(FlavoredItems.PASTA))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.PESTO_PASTA)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.RAGU_PASTA, 1)
                .requires(Ingredient.of(FlavoredItems.RED_TOMATO), 2)
                .requires(Ingredient.of(FlavoredItems.COOKED_GROUND_BEEF))
                .requires(Ingredient.of(Items.CARROT))
                .requires(Ingredient.of(FlavoredItems.PASTA))
                .unlockedBy(getItemName(FlavoredItems.PASTA.get()),
                        has(FlavoredItems.PASTA))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.RAGU_PASTA)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.of(Items.WATER_BUCKET), Items.RABBIT_STEW, 1)
                .requires(Ingredient.of(Items.BAKED_POTATO))
                .requires(Ingredient.of(Items.COOKED_RABBIT))
                .requires(Ingredient.of(Items.CARROT))
                .requires(Ingredient.of(Items.BROWN_MUSHROOM))
                .unlockedBy(getItemName(Items.COOKED_RABBIT),
                        has(Items.COOKED_RABBIT))
                .save(recipeOutput, mixing(getItemName(Items.RABBIT_STEW)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.of(Items.WATER_BUCKET), Items.MUSHROOM_STEW, 1)
                .requires(Ingredient.of(Items.RED_MUSHROOM))
                .requires(Ingredient.of(Items.BROWN_MUSHROOM))
                .unlockedBy(getItemName(Items.BROWN_MUSHROOM),
                        has(Items.BROWN_MUSHROOM))
                .save(recipeOutput, mixing(getItemName(Items.MUSHROOM_STEW)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.of(Items.WATER_BUCKET), Items.BEETROOT_SOUP, 1)
                .requires(Ingredient.of(Items.BEETROOT), 3)
                .unlockedBy(getItemName(Items.BEETROOT),
                        has(Items.BEETROOT))
                .save(recipeOutput, mixing(getItemName(Items.BEETROOT_SOUP)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.TOMATO_PASTA, 1)
                .requires(Ingredient.of(FlavoredItems.RED_TOMATO), 2)
                .requires(Ingredient.of(FlavoredItems.GARLIC))
                .requires(Ingredient.of(FlavoredItems.PASTA))
                .unlockedBy(getItemName(FlavoredItems.PASTA.get()),
                        has(FlavoredItems.PASTA))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.TOMATO_PASTA)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.of(Items.MILK_BUCKET), FlavoredItems.CEREAL, 1)
                .requires(Ingredient.of(FlavoredItems.CINNAMON), 2)
                .requires(Ingredient.of(Items.WHEAT), 3)
                .requires(Ingredient.of(Items.SUGAR))
                .unlockedBy(getItemName(Items.WHEAT),
                        has(Items.WHEAT))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.CEREAL)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.EMPTY, Ingredient.EMPTY, FlavoredItems.COOKIE_DOUGH, 1)
                .requires(Ingredient.of(FlavoredItems.FLOUR), 2)
                .requires(Ingredient.of(FlavoredItems.CHOCOLATE))
                .requires(Ingredient.of(FlavoredItems.BUTTER))
                .requires(Ingredient.of(Items.SUGAR))
                .unlockedBy(getItemName(FlavoredItems.FLOUR),
                        has(FlavoredItems.FLOUR))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.COOKIE_DOUGH)));

        new MixingRecipeBuilder(RecipeCategory.MISC, Ingredient.EMPTY, Ingredient.EMPTY, FlavoredItems.PASTA, 1)
                .requires(Ingredient.of(FlavoredItems.FLOUR), 2)
                .requires(Ingredient.of(Items.EGG), 2)
                .unlockedBy(getItemName(FlavoredItems.FLOUR),
                        has(FlavoredItems.FLOUR))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.PASTA)));

        new MixingRecipeBuilder(RecipeCategory.MISC, Ingredient.EMPTY, Ingredient.EMPTY, FlavoredItems.PASTRY_DOUGH, 1)
                .requires(Ingredient.of(FlavoredItems.FLOUR), 2)
                .requires(Ingredient.of(Items.EGG))
                .requires(Ingredient.of(FlavoredItems.BUTTER))
                .unlockedBy(getItemName(FlavoredItems.FLOUR),
                        has(FlavoredItems.FLOUR))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.PASTRY_DOUGH)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.of(Items.WATER_BUCKET), FlavoredItems.POLENTA, 1)
                .requires(Ingredient.of(FlavoredItems.CORN), 3)
                .requires(Ingredient.of(FlavoredItems.BUTTER))
                .unlockedBy(getItemName(FlavoredItems.CORN),
                        has(FlavoredItems.CORN))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.POLENTA)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.of(Items.WATER_BUCKET), FlavoredItems.PORRIDGE, 1)
                .requires(Ingredient.of(Items.WHEAT), 3)
                .unlockedBy(getItemName(Items.WHEAT),
                        has(Items.WHEAT))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.PORRIDGE)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.SALAD, 1)
                .requires(Ingredient.of(FlavoredItemTags.TOMATOES))
                .requires(Ingredient.of(FlavoredItems.SPINACH), 2)
                .requires(Ingredient.of(FlavoredItemTags.SALAD_FINISHINGS))
                .unlockedBy(getItemName(FlavoredItems.SPINACH),
                        has(FlavoredItems.SPINACH))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.SPINACH)));

        new MixingRecipeBuilder(RecipeCategory.FOOD, Ingredient.of(Items.BOWL), Ingredient.EMPTY, FlavoredItems.SHAKSHOUKA, 1)
                .requires(Ingredient.of(FlavoredItems.DRIED_PEPPER))
                .requires(Ingredient.of(FlavoredItems.RED_TOMATO), 2)
                .requires(Ingredient.of(FlavoredItems.GARLIC))
                .requires(Ingredient.of(Items.EGG))
                .unlockedBy(getItemName(FlavoredItems.RED_TOMATO),
                        has(FlavoredItems.RED_TOMATO))
                .save(recipeOutput, mixing(getItemName(FlavoredItems.SHAKSHOUKA)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, new ItemStack(FlavoredBlocks.PIZZA.asItem()), 3.0F, 200)
                .define('#', Ingredient.of(FlavoredItems.DOUGH.get()))
                .define('X', Ingredient.of(FlavoredItems.RED_TOMATO.get()))
                .define('*', Ingredient.of(FlavoredItems.SOFT_CHEESE_SLICE.get()))
                .pattern("X*X")
                .pattern(" # ")
                .unlockedBy("has_soft_cheese_slice", has(FlavoredItems.SOFT_CHEESE_SLICE))
                .save(recipeOutput, baking(getItemName(FlavoredBlocks.PIZZA)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, Items.BREAD, 2, 0.35F, 200)
                .define('#', Ingredient.of(FlavoredItems.DOUGH.get()))
                .pattern("#")
                .unlockedBy("has_dough", has(FlavoredItems.DOUGH))
                .save(recipeOutput, baking(getItemName(Items.BREAD)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, new ItemStack(FlavoredItems.GARLIC_BREAD.asItem()), 1.0F, 200)
                .define('#', Ingredient.of(FlavoredItems.DOUGH.get()))
                .define('X', Ingredient.of(FlavoredItems.GARLIC.get()))
                .define('*', Ingredient.of(FlavoredItems.BUTTER.get()))
                .pattern("X*X")
                .pattern(" # ")
                .unlockedBy("has_garlic", has(FlavoredItems.GARLIC))
                .save(recipeOutput, baking(getItemName(FlavoredItems.GARLIC_BREAD)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, Items.CAKE, 2, 2.0F, 200)
                .define('#', Ingredient.of(FlavoredItems.BATTER.get()))
                .pattern(" # ")
                .unlockedBy("has_batter", has(FlavoredItems.BATTER))
                .save(recipeOutput, baking(getItemName(Items.CAKE)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, new ItemStack(FlavoredBlocks.PUDDING.asItem()), 3.0F, 200)
                .define('#', Ingredient.of(FlavoredItems.BATTER.get()))
                .define('X', Ingredient.of(FlavoredItems.CHOCOLATE.get()))
                .define('*', Ingredient.of(Items.EGG))
                .pattern("X*X")
                .pattern(" # ")
                .unlockedBy("has_batter", has(FlavoredItems.DOUGH))
                .save(recipeOutput, baking(getItemName(FlavoredBlocks.PUDDING)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, Items.COOKIE, 8, 1.0F, 200)
                .define('#', Ingredient.of(FlavoredItems.COOKIE_DOUGH.get()))
                .pattern("#")
                .unlockedBy("has_cookie_dough", has(FlavoredItems.COOKIE_DOUGH))
                .save(recipeOutput, baking(getItemName(Items.COOKIE)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, FlavoredItems.BUTTER_PASTRY, 1, 2.0F, 200)
                .define('X', Ingredient.of(FlavoredItems.BUTTER.get()))
                .define('#', Ingredient.of(FlavoredItems.PASTRY_DOUGH.get()))
                .pattern("XXX")
                .pattern(" # ")
                .unlockedBy("has_pastry_dough", has(FlavoredItems.PASTRY_DOUGH))
                .save(recipeOutput, baking(getItemName(FlavoredItems.BUTTER_PASTRY)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE_PASTRY, 1, 2.0F, 200)
                .define('X', Ingredient.of(FlavoredItems.CHOCOLATE.get()))
                .define('#', Ingredient.of(FlavoredItems.PASTRY_DOUGH.get()))
                .pattern("XXX")
                .pattern(" # ")
                .unlockedBy("has_pastry_dough", has(FlavoredItems.PASTRY_DOUGH))
                .save(recipeOutput, baking(getItemName(FlavoredItems.CHOCOLATE_PASTRY)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, FlavoredItems.CINNAMON_PASTRY, 1, 2.0F, 200)
                .define('X', Ingredient.of(FlavoredItems.CINNAMON.get()))
                .define('#', Ingredient.of(FlavoredItems.PASTRY_DOUGH.get()))
                .pattern("XXX")
                .pattern(" # ")
                .unlockedBy("has_pastry_dough", has(FlavoredItems.PASTRY_DOUGH))
                .save(recipeOutput, baking(getItemName(FlavoredItems.CINNAMON_PASTRY)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, FlavoredItems.HONEY_PASTRY, 1, 2.0F, 200)
                .define('X', Ingredient.of(Items.HONEYCOMB))
                .define('#', Ingredient.of(FlavoredItems.PASTRY_DOUGH.get()))
                .pattern("XXX")
                .pattern(" # ")
                .unlockedBy("has_pastry_dough", has(FlavoredItems.PASTRY_DOUGH))
                .save(recipeOutput, baking(getItemName(FlavoredItems.HONEY_PASTRY)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, Items.PUMPKIN_PIE, 1, 2.0F, 200)
                .define('X', Ingredient.of(Items.PUMPKIN))
                .define('#', Ingredient.of(FlavoredItems.PASTRY_DOUGH.get()))
                .pattern(" X ")
                .pattern(" # ")
                .unlockedBy("has_pastry_dough", has(FlavoredItems.PASTRY_DOUGH))
                .save(recipeOutput, baking(getItemName(Items.PUMPKIN_PIE)));

        new BakeRecipeBuilder(RecipeCategory.FOOD, FlavoredItems.OSSOBUCO, 2, 3.0F, 200)
                .define('#', Ingredient.of(Items.BOWL))
                .define('X', Ingredient.of(Items.CARROT))
                .define('*', Ingredient.of(FlavoredItems.MUTTON_SHANK))
                .define('C', Ingredient.of(FlavoredItemTags.WINES))
                .pattern(" X ")
                .pattern("*C*")
                .pattern(" # ")
                .unlockedBy("has_mutton_shank", has(FlavoredItems.MUTTON_SHANK))
                .save(recipeOutput, baking(getItemName(FlavoredItems.OSSOBUCO)));


        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 2).requires(Items.COCOA_BEANS).requires(Items.COCOA_BEANS).requires(Items.COCOA_BEANS).requires(Items.SUGAR).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(Items.COCOA_BEANS), has(Items.COCOA_BEANS)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHOCOLATE)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 4).requires(FlavoredBlocks.CHOCOLATE_BLOCK).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_BLOCK), has(FlavoredBlocks.CHOCOLATE_BLOCK)).save(recipeOutput, crafting("chocolate_from_block"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHOCOLATE, 1).requires(FlavoredItems.CHOCOLATE_EGG).group(getItemName(FlavoredItems.CHOCOLATE)).unlockedBy(getHasName(FlavoredItems.CHOCOLATE_EGG), has(FlavoredItems.CHOCOLATE_EGG)).save(recipeOutput, crafting("chocolate_from_egg"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_BLOCK).define('#', FlavoredItems.CHOCOLATE).pattern("##").pattern("##").unlockedBy(getHasName(FlavoredItems.CHOCOLATE), has(FlavoredItems.CHOCOLATE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_BLOCK)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_TILES, 4).define('#', FlavoredBlocks.CHOCOLATE_BLOCK).pattern("##").pattern("##").group(getItemName(FlavoredBlocks.CHOCOLATE_BLOCK)).unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_BLOCK), has(FlavoredBlocks.CHOCOLATE_BLOCK)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_TILES)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_TILE_STAIRS, 4).define('#', FlavoredBlocks.CHOCOLATE_TILES).pattern("#  ").pattern("## ").pattern("###").unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_TILES), has(FlavoredBlocks.CHOCOLATE_TILES)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_TILE_STAIRS)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, FlavoredBlocks.CHOCOLATE_TILE_SLAB, 6).define('#', FlavoredBlocks.CHOCOLATE_TILES).pattern("###").unlockedBy(getHasName(FlavoredBlocks.CHOCOLATE_TILES), has(FlavoredBlocks.CHOCOLATE_TILES)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.CHOCOLATE_TILE_SLAB)));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, FlavoredItems.KNIFE).define('#', Items.STICK).define('X', Items.IRON_INGOT).pattern("X").pattern("#").unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(recipeOutput, crafting(getItemName(FlavoredItems.KNIFE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FlavoredItems.WHISK).define('#', Items.IRON_INGOT).define('X', Items.IRON_NUGGET).pattern("X").pattern("X").pattern("#").unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(recipeOutput, crafting(getItemName(FlavoredItems.WHISK)));


        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.SOFT_CHEESE).define('#', FlavoredItems.SOFT_CHEESE_SLICE).pattern("##").pattern("##").group(getItemName(FlavoredBlocks.SOFT_CHEESE)).unlockedBy(getHasName(FlavoredItems.SOFT_CHEESE_SLICE), has(FlavoredItems.SOFT_CHEESE_SLICE)).save(recipeOutput, crafting("soft_cheese_from_slices"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.AGED_CHEESE).define('#', FlavoredItems.AGED_CHEESE_SLICE).pattern("##").pattern("##").unlockedBy(getHasName(FlavoredItems.AGED_CHEESE_SLICE), has(FlavoredItems.AGED_CHEESE_SLICE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.AGED_CHEESE)));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredBlocks.PIZZA).define('#', FlavoredItems.PIZZA_SLICE).pattern("##").pattern("##").group(getItemName(FlavoredItems.PIZZA_SLICE)).unlockedBy(getHasName(FlavoredItems.PIZZA_SLICE), has(FlavoredItems.PIZZA_SLICE)).save(recipeOutput, crafting("pizza_from_slices"));


        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FlavoredBlocks.OVEN).define('#', Blocks.COBBLED_DEEPSLATE).pattern("###").define('X', Blocks.PACKED_MUD).pattern("X X").pattern("XXX").unlockedBy(getHasName(Blocks.COBBLED_DEEPSLATE), has(Blocks.COBBLED_DEEPSLATE)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.OVEN)));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FlavoredBlocks.KEG).define('#', ItemTags.PLANKS).define('X', Items.COPPER_INGOT).pattern("X#X").pattern("X X").pattern("X#X").unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.KEG)));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FlavoredBlocks.MIXING_BOWL).define('#', Items.IRON_INGOT).define('X', Items.IRON_NUGGET).pattern("X X").pattern("###").unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(recipeOutput, crafting(getItemName(FlavoredBlocks.MIXING_BOWL)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.FLOUR).requires(Items.WHEAT).unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(recipeOutput, crafting(getItemName(FlavoredItems.FLOUR)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.BUTTER).requires(Items.MILK_BUCKET).unlockedBy(getHasName(Items.MILK_BUCKET), has(Items.MILK_BUCKET)).save(recipeOutput, crafting(getItemName(FlavoredItems.BUTTER)));


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.TOMATO_SEEDS).requires(FlavoredItemTags.TOMATOES).unlockedBy(getHasName(FlavoredItems.RED_TOMATO), has(FlavoredItemTags.TOMATOES)).save(recipeOutput, crafting(getItemName(FlavoredItems.TOMATO_SEEDS)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.SPINACH_SEEDS).requires(FlavoredItems.SPINACH).unlockedBy(getHasName(FlavoredItems.SPINACH), has(FlavoredItems.SPINACH)).save(recipeOutput, crafting(getItemName(FlavoredItems.SPINACH_SEEDS)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.CORN_SEEDS).requires(FlavoredItems.CORN).unlockedBy(getHasName(FlavoredItems.CORN), has(FlavoredItems.CORN)).save(recipeOutput, crafting(getItemName(FlavoredItems.CORN_SEEDS)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FlavoredItems.PEPPER_SEEDS).requires(FlavoredItems.PEPPER).unlockedBy(getHasName(FlavoredItems.PEPPER), has(FlavoredItems.PEPPER)).save(recipeOutput, crafting(getItemName(FlavoredItems.PEPPER_SEEDS)));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHEESE_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.BUTTER).requires(FlavoredItems.AGED_CHEESE_SLICE, 2).unlockedBy(getHasName(FlavoredItems.AGED_CHEESE_SLICE), has(FlavoredItems.AGED_CHEESE_SLICE)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHEESE_SANDWICH)));


        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.HAMBURGER, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(FlavoredItems.COOKED_GROUND_BEEF).requires(FlavoredItems.COOKED_GROUND_BEEF).requires(FlavoredItems.RED_TOMATO).requires(FlavoredItems.AGED_CHEESE_SLICE).unlockedBy(getHasName(Items.COOKED_BEEF), has(Items.COOKED_BEEF)).save(recipeOutput, crafting(getItemName(FlavoredItems.HAMBURGER)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.HAM_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(Items.HONEYCOMB).requires(FlavoredItems.COOKED_PORK_JOWL, 2).unlockedBy(getHasName(FlavoredItems.COOKED_PORK_JOWL), has(FlavoredItems.COOKED_PORK_JOWL)).save(recipeOutput, crafting(getItemName(FlavoredItems.HAM_SANDWICH)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.CHICKEN_SANDWICH, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(FlavoredItems.YELLOW_TOMATO).requires(FlavoredItems.COOKED_CHICKEN_DRUMSTICK, 2).unlockedBy(getHasName(FlavoredItems.COOKED_CHICKEN_DRUMSTICK), has(FlavoredItems.COOKED_CHICKEN_DRUMSTICK)).save(recipeOutput, crafting(getItemName(FlavoredItems.CHICKEN_SANDWICH)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FlavoredItems.SHAWARMA, 2).requires(Items.BREAD).requires(FlavoredItems.SPINACH).requires(FlavoredItems.RED_TOMATO).requires(FlavoredItems.GARLIC).requires(FlavoredItems.COOKED_MUTTON_SHANK, 2).unlockedBy(getHasName(FlavoredItems.COOKED_MUTTON_SHANK), has(FlavoredItems.COOKED_MUTTON_SHANK)).save(recipeOutput, crafting(getItemName(FlavoredItems.SHAWARMA)));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.SWEET_BERRY_JUICE).define('#', Items.GLASS_BOTTLE).define('X', Items.SWEET_BERRIES).pattern(" X ").pattern("X#X").unlockedBy(getHasName(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES)).save(recipeOutput, crafting(getItemName(FlavoredItems.SWEET_BERRY_JUICE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.GLOW_BERRY_JUICE).define('#', Items.GLASS_BOTTLE).define('X', Items.GLOW_BERRIES).pattern(" X ").pattern("X#X").unlockedBy(getHasName(Items.GLOW_BERRIES), has(Items.GLOW_BERRIES)).save(recipeOutput, crafting(getItemName(FlavoredItems.GLOW_BERRY_JUICE)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.WORT).define('#', Items.GLASS_BOTTLE).define('X', Items.WHEAT).pattern(" X ").pattern("X#X").unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT)).save(recipeOutput, crafting(getItemName(FlavoredItems.WORT)));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.APPLE_JUICE).define('#', Items.GLASS_BOTTLE).define('X', Items.APPLE).pattern("X#X").unlockedBy(getHasName(Items.APPLE), has(Items.APPLE)).save(recipeOutput, crafting(getItemName(FlavoredItems.APPLE_JUICE)));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FlavoredItems.HOT_SAUCE).define('#', Items.GLASS_BOTTLE).define('X', FlavoredItems.PEPPER).define('*', FlavoredItems.DRIED_PEPPER).pattern(" * ").pattern("X#X").pattern(" * ").unlockedBy(getHasName(FlavoredItems.DRIED_PEPPER), has(FlavoredItems.DRIED_PEPPER)).save(recipeOutput, crafting(getItemName(FlavoredItems.HOT_SAUCE)));

    }
}