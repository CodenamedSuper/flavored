package com.sidden.flavored.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class BakingRecipe implements Recipe<CraftingInput> {
    private final String group;
    private final ShapedRecipePattern pattern;
    private final ItemStack result;
    private final float experience;
    private final int bakingTime;

    public BakingRecipe(String group, ShapedRecipePattern pattern, ItemStack result, float experience, int bakingTime) {
        this.group = group;
        this.pattern = pattern;
        this.result = result;
        this.experience = experience;
        this.bakingTime = bakingTime;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return this.getResultItem(registries).copy();
    }

    @Override
    public boolean isIncomplete() {
        NonNullList<Ingredient> ingredients = this.getIngredients();
        return ingredients.isEmpty() || ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).anyMatch(Ingredient::hasNoItems);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.pattern.width() && height >= this.pattern.height();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.pattern.ingredients();
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FlavoredRecipeTypes.OVEN_SERIALIZER.value();
    }

    public int getBakingTime() {
        return this.bakingTime;
    }

    @Override
    public RecipeType<?> getType() {
        return FlavoredRecipeTypes.OVEN_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<BakingRecipe> {
        private static final MapCodec<BakingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group), ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern), ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience), Codec.INT.fieldOf("bakingtime").orElse(200).forGetter(recipe -> recipe.bakingTime)).apply(instance, BakingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, BakingRecipe> STREAM_CODEC = StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<BakingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BakingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static BakingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(buffer);
            float experience = buffer.readFloat();
            int bakingTime = buffer.readVarInt();
            return new BakingRecipe(group, pattern, result, experience, bakingTime);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, BakingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.bakingTime);
        }
    }
}
