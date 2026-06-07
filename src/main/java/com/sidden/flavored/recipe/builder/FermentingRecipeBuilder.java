package com.sidden.flavored.recipe.builder;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.FermentingBookCategory;
import com.sidden.flavored.recipe.FermentingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
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
import java.util.Objects;

public class FermentingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final Ingredient ingredient;
    private final Ingredient fermenter;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String particleColor;
    @Nullable
    private String group;

    public FermentingRecipeBuilder(RecipeCategory category, ItemLike result, int count, Ingredient ingredient, Ingredient fermenter) {
        this.category = category;
        this.result = result.asItem();
        this.count = count;
        this.ingredient = ingredient;
        this.fermenter = fermenter;
    }

    public static FermentingRecipeBuilder fermenting(Ingredient ingredient, Ingredient fermenter, RecipeCategory category, ItemLike result) {
        return new FermentingRecipeBuilder(category, result, 1, ingredient, fermenter);
    }

    public static FermentingRecipeBuilder fermenting(Ingredient ingredient, Ingredient fermenter, RecipeCategory category, ItemLike result, int count) {
        return new FermentingRecipeBuilder(category, result, count, ingredient, fermenter);
    }

    private static FermentingBookCategory determineBookCategory(RecipeCategory category) {
        return category == RecipeCategory.FOOD ? FermentingBookCategory.FOOD : FermentingBookCategory.MISC;
    }

    private static ResourceLocation getDefaultRecipeId(ItemLike itemLike) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(itemLike.asItem());
        return ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "fermenting/" + id.getPath());
    }

    public FermentingRecipeBuilder particleColor(String particleColor) {
        this.particleColor = particleColor;
        return this;
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
        this.ensureValid(id);
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        FermentingRecipe recipe = new FermentingRecipe(
                Objects.requireNonNullElse(this.group, ""),
                determineBookCategory(this.category),
                this.ingredient,
                this.fermenter,
                Objects.requireNonNullElse(this.particleColor, FermentingRecipe.DEFAULT_PARTICLE_COLOR),
                new ItemStack(this.result, this.count)
        );
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }

    @Override
    public void save(RecipeOutput recipeOutput) {
        this.save(recipeOutput, getDefaultRecipeId(this.result));
    }

    @Override
    public void save(RecipeOutput recipeOutput, String id) {
        ResourceLocation defaultId = getDefaultRecipeId(this.result);
        ResourceLocation parsedId = ResourceLocation.parse(id);
        if (parsedId.equals(defaultId)) {
            throw new IllegalStateException("Recipe " + id + " should remove its 'save' argument as it is equal to default one");
        } else {
            this.save(recipeOutput, parsedId);
        }
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
