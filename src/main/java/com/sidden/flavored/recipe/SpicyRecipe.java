package com.sidden.flavored.recipe;

import com.sidden.flavored.registry.FlavoredDataComponents;
import com.sidden.flavored.registry.FlavoredItemTags;
import com.sidden.flavored.registry.FlavoredItems;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.*;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class SpicyRecipe extends CustomRecipe {
    private static final Ingredient SPICE_INGREDIENT;

    public SpicyRecipe(CraftingBookCategory category) {
        super(category);
    }

    public boolean matches(CraftingInput input, Level level) {

        int foodCount = 0;
        int spiceCount = 0;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemstack = input.getItem(i);
            if (!itemstack.isEmpty()) {

                if (SPICE_INGREDIENT.test(itemstack)) {
                    spiceCount++;
                }
                else if (!itemstack.is(FlavoredItemTags.NOT_SPICEABLE) && itemstack.has(DataComponents.FOOD)) {
                    foodCount++;
                }

            }
        }

        return foodCount == 1 && spiceCount >= 1 && spiceCount < 4;
    }

    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        int spiciness = 0;
        ItemStack result = ItemStack.EMPTY;

        for(int j = 0; j < input.size(); ++j) {
            ItemStack itemstack = input.getItem(j);

            if (SPICE_INGREDIENT.test(itemstack)) {
                spiciness++;
            }
            else if (!itemstack.is(FlavoredItemTags.NOT_SPICEABLE) && itemstack.has(DataComponents.FOOD)) {
                result = itemstack.copy();
            }
        }

        result.set(FlavoredDataComponents.SPICINESS, spiciness);
        result.setCount(1);
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        return super.getRemainingItems(input);
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }


    public RecipeSerializer<?> getSerializer() {
        return FlavoredRecipeTypes.SPICING.get();
    }

    static {
        SPICE_INGREDIENT = Ingredient.of(new ItemLike[]{FlavoredItems.DRIED_PEPPER});

    }

}