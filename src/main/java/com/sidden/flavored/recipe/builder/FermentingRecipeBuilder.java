package com.sidden.flavored.recipe.builder;

import com.sidden.flavored.recipe.FermentingRecipe;
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

public class FermentingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String colorParticle;
    @javax.annotation.Nullable
    private String group;

    public FermentingRecipeBuilder(RecipeCategory category, String colorParticle, Ingredient fermenter, Ingredient ingredient, ItemLike result, int count) {
        this(category, new ItemStack(result, count));
        ingredients.add(0, ingredient);
        ingredients.add(1, fermenter);
        this.colorParticle = colorParticle;
    }

    public FermentingRecipeBuilder(RecipeCategory recipeCategory, ItemStack result) {
        this.result = result.getItem();
        this.count = result.getCount();
    }

    @Override
    public FermentingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public FermentingRecipeBuilder group(@Nullable String group) {
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
                .requirements(AdvancementRequirements.Strategy.AND);
        this.criteria.forEach(advancement::addCriterion);
        FermentingRecipe recipe = new FermentingRecipe(ingredients.get(0), ingredients.get(1), colorParticle, this.getResult().getDefaultInstance());
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
