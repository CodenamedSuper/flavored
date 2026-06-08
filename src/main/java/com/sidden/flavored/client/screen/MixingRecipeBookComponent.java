package com.sidden.flavored.client.screen;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.MixingRecipe;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nullable;
import java.util.List;

public class MixingRecipeBookComponent extends RecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/mixing_bowl_filter_enabled"),
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/mixing_bowl_filter_disabled"),
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/mixing_bowl_filter_enabled_highlighted"),
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/mixing_bowl_filter_disabled_highlighted")
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
        this.ghostRecipe.setRecipe(recipe);

        NonNullList<Ingredient> ingredients = recipe.value().getIngredients();
        for (int i = 0; i < ingredients.size() - 2; i++) {
            Ingredient ingredient = ingredients.get(i);
            if (!ingredient.isEmpty()) {
                Slot slot = slots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
            }
        }

        if (recipe.value() instanceof MixingRecipe mixingRecipe) {
            if (!mixingRecipe.vesselInput().isEmpty()) {
                this.ghostRecipe.addIngredient(mixingRecipe.vesselInput(), slots.get(6).x, slots.get(6).y);
            }
            if (!mixingRecipe.liquidInput().isEmpty()) {
                this.ghostRecipe.addIngredient(mixingRecipe.liquidInput(), slots.get(7).x, slots.get(7).y);
            }
        }
    }
}
