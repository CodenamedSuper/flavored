package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import com.codenamed.flavored.recipe.FermentingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FlavoredRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Flavored.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Flavored.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FermentingRecipe>> FERMENTER_SERIALIZER =
            SERIALIZERS.register("fermenting", FermentingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FermentingRecipe>> FERMENTER_TYPE =
            TYPES.register("fermenting", () -> new RecipeType<FermentingRecipe>() {
                @Override
                public String toString() {
                    return "fermenting";
                }
            });

    public static void init(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }

}
