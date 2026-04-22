package com.sidden.flavored.recipe.input;

import com.sidden.flavored.block.property.MixingBowlLiquid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record MixingRecipeInput(List<ItemStack> ingredientInputs, ItemStack vesselInput, MixingBowlLiquid liquid) implements RecipeInput {
    @Override
    public ItemStack getItem(int i) {

        if (i < ingredientInputs().size()) {
            return ingredientInputs().get(i);
        }
        else if(i >= 6) {
            return vesselInput();
        }

        return ItemStack.EMPTY;
    }

    public ItemStack getVessel() {
        return vesselInput();
    }

    public MixingBowlLiquid getLiquid() {
        return liquid();
    }

    @Override
    public int size() {
        return 7;
    }
}
