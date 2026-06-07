package com.sidden.flavored.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sidden.flavored.recipe.input.FermentingRecipeInput;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record FermentingRecipe(String group, FermentingBookCategory category, Ingredient ingredient, Ingredient fermenter, String particleColor, ItemStack result) implements Recipe<FermentingRecipeInput> {
    public static final String DEFAULT_PARTICLE_COLOR = "349ad6";

    @Override
    public boolean matches(FermentingRecipeInput input, Level level) {
        return this.ingredient.test(input.mainInput());
    }

    @Override
    public ItemStack assemble(FermentingRecipeInput input, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.ingredient);
        list.add(this.fermenter);
        return list;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    @Override
    public ItemStack getToastSymbol() {
        return FlavoredBlocks.KEG.toStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FlavoredRecipeTypes.KEG_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FlavoredRecipeTypes.KEG_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FermentingRecipe> {
        public static final MapCodec<FermentingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(FermentingRecipe::group),
                FermentingBookCategory.CODEC.fieldOf("category").forGetter(FermentingRecipe::category),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FermentingRecipe::ingredient),
                Ingredient.CODEC_NONEMPTY.fieldOf("fermenter").forGetter(FermentingRecipe::fermenter),
                Codec.STRING.optionalFieldOf("particle_color", DEFAULT_PARTICLE_COLOR).forGetter(FermentingRecipe::particleColor),
                ItemStack.CODEC.fieldOf("result").forGetter(FermentingRecipe::result)
        ).apply(inst, FermentingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        private static FermentingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            FermentingBookCategory category = buffer.readEnum(FermentingBookCategory.class);
            Ingredient mainInput = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient fermentingInput = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            String particleColor = buffer.readUtf();
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new FermentingRecipe(group, category, mainInput, fermentingInput, particleColor, output);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, FermentingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.fermenter);
            buffer.writeUtf(recipe.particleColor);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        }

        @Override
        public MapCodec<FermentingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FermentingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
