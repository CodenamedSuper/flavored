package com.sidden.flavored.client.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sidden.flavored.block.property.MixingBowlLiquid;
import com.sidden.flavored.client.recipe.input.MixingRecipeInput;
import com.sidden.flavored.registry.FlavoredRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record MixingRecipe(List<Ingredient> ingredientsInput, Ingredient vesselInput, MixingBowlLiquid liquidInput, ItemStack output) implements Recipe<MixingRecipeInput> {


    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(ingredientsInput());
        list.add(vesselInput);
        return list;
    }

    @Override
    public boolean matches(MixingRecipeInput input, Level level) {
        if (level.isClientSide()) return false;

        List<ItemStack> remaining = new ArrayList<>(input.ingredientInputs());

        for (Ingredient ingredient : ingredientsInput()) {
            boolean matched = false;

            Iterator<ItemStack> it = remaining.iterator();
            while (it.hasNext()) {
                ItemStack stack = it.next();

                if (!stack.isEmpty() && ingredient.test(stack)) {
                    it.remove();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        for (ItemStack stack : remaining) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        if (!vesselInput().isEmpty()) {
            if (!vesselInput().test(input.getVessel())) {
                return false;
            }
        }

        if (liquidInput() != input.getLiquid()) {
            return false;
        }

        return true;
    }

    @Override
    public ItemStack assemble(MixingRecipeInput mixingRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FlavoredRecipes.MIXING_BOWL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return FlavoredRecipes.MIXING_BOWL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MixingRecipe> {

        public static final MapCodec<MixingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").forGetter(MixingRecipe::ingredientsInput),
                Ingredient.CODEC_NONEMPTY.optionalFieldOf("vessel", Ingredient.EMPTY).forGetter(MixingRecipe::vesselInput),
                MixingBowlLiquid.CODEC.fieldOf("liquid").forGetter(MixingRecipe::liquidInput),
                ItemStack.CODEC.fieldOf("result").forGetter(MixingRecipe::output)
        ).apply(inst, MixingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                        MixingRecipe::ingredientsInput,

                        Ingredient.CONTENTS_STREAM_CODEC,
                        MixingRecipe::vesselInput,

                        MixingBowlLiquid.STREAM_CODEC,
                        MixingRecipe::liquidInput,

                        ItemStack.STREAM_CODEC,
                        MixingRecipe::output,

                        MixingRecipe::new
                );

        @Override
        public MapCodec<MixingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MixingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
