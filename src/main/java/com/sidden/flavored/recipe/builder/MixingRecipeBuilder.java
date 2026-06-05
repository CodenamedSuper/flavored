package com.sidden.flavored.recipe.builder;

import com.sidden.flavored.recipe.MixingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MixingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private Ingredient vessel;
    private Ingredient liquid;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @javax.annotation.Nullable
    private String group;

    public MixingRecipeBuilder(RecipeCategory category, Ingredient vessel, Ingredient liquid, ItemLike result, int count) {
        this(category, new ItemStack(result, count));
        this.vessel = vessel;
        this.liquid = liquid;
    }

    public MixingRecipeBuilder(RecipeCategory recipeCategory, ItemStack result) {
        this.result = result.getItem();
        this.count = result.getCount();
    }

    public MixingRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public MixingRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    @Override
    public MixingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public MixingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        MixingRecipe recipe = new MixingRecipe(ingredients, vessel, liquid, this.getResult().getDefaultInstance());
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
