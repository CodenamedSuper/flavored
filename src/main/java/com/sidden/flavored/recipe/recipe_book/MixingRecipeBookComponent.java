package com.sidden.flavored.recipe.recipe_book;

import com.sidden.flavored.recipe.MixingRecipe;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class MixingRecipeBookComponent extends RecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted")
    );
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.mixable");

    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_SPRITES);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }


    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipe, List<Slot> slots) {
        ItemStack itemstack = recipe.value().getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(recipe);

        NonNullList<Ingredient> nonnulllist = recipe.value().getIngredients();
        Iterator<Ingredient> iterator = nonnulllist.iterator();

        for (int i = 0; i < 8; i++) {
            if (!iterator.hasNext()) {
                return;
            }
            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                if (recipe.value() instanceof MixingRecipe mixingRecipe) {
                    if (ingredient == mixingRecipe.liquidInput()) {
                        Slot slot1 = slots.get(7);
                        this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
                    } else if (ingredient == mixingRecipe.vesselInput()) {
                        Slot slot1 = slots.get(6);
                        this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
                    } else {
                        Slot slot1 = slots.get(i);
                        this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
                    }
                }
            }
        }
    }
}
