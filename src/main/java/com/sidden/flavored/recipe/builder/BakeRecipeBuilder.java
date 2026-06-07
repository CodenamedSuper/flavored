package com.sidden.flavored.recipe.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sidden.flavored.recipe.BakingRecipe;
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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BakeRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final int count;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @javax.annotation.Nullable
    private String group;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final float experience;
    private final int bakingTime;


    public BakeRecipeBuilder(RecipeCategory category, ItemLike result, int count, float experience, int bakingTime) {
        this(category, new ItemStack(result, count), experience, bakingTime);
    }

    public BakeRecipeBuilder(RecipeCategory recipeCategory, ItemStack result, float experience, int bakingTime) {
        this.result = result.getItem();
        this.count = result.getCount();
        this.experience = experience;
        this.bakingTime = bakingTime;
    }

    public BakeRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        return this.define(symbol, Ingredient.of(tag));
    }


    public BakeRecipeBuilder define(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }


    public BakeRecipeBuilder pattern(String pattern) {
        if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(pattern);
            return this;
        }
    }

    @Override
    public BakeRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public BakeRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    private ShapedRecipePattern ensureValid(ResourceLocation loaction) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + loaction);
        } else {
            return ShapedRecipePattern.of(this.key, this.rows);
        }
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.AND);

        ShapedRecipePattern shapedrecipepattern = this.ensureValid(id);

        this.criteria.forEach(advancement::addCriterion);
        BakingRecipe recipe = new BakingRecipe(group, shapedrecipepattern, this.getResult().getDefaultInstance(), experience, bakingTime);
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
