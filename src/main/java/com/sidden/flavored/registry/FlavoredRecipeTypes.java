package com.sidden.flavored.registry;


import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.BakingRecipe;
import com.sidden.flavored.recipe.FermentingRecipe;
import com.sidden.flavored.recipe.MixingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Flavored.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Flavored.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FermentingRecipe>> KEG_SERIALIZER =
            SERIALIZERS.register("fermenting", FermentingRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MixingRecipe>> MIXING_BOWL_SERIALIZER =
            SERIALIZERS.register("mixing", MixingRecipe.Serializer::new);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BakingRecipe>> OVEN_SERIALIZER = SERIALIZERS.register("baking", BakingRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<FermentingRecipe>> KEG_TYPE =
            TYPES.register("fermenting", () -> new RecipeType<FermentingRecipe>() {
                @Override
                public String toString() {
                    return "fermenting";
                }
            });

    public static final DeferredHolder<RecipeType<?>, RecipeType<MixingRecipe>> MIXING_BOWL_TYPE =
            TYPES.register("mixing", () -> new RecipeType<MixingRecipe>() {
                @Override
                public String toString() {
                    return "mixing";
                }
            });

    public static final DeferredHolder<RecipeType<?>, RecipeType<BakingRecipe>> OVEN_TYPE = TYPES.register("baking", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "baking";
        }
    });

    public static void init(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }

}
