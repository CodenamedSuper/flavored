package com.sidden.flavored.client.screen;

import com.sidden.flavored.Flavored;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BakingRecipeBookComponent extends RecipeBookComponent {
    private static final WidgetSprites FILTER_SPRITES = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/oven_filter_enabled"),
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/oven_filter_disabled"),
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/oven_filter_enabled_highlighted"),
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "recipe_book/oven_filter_disabled_highlighted")
    );
    private static final Component FILTER_NAME = Component.translatable("gui.recipebook.toggleRecipes.bakable");

    @Nullable
    private Ingredient fuels;


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
        this.ghostRecipe.addIngredient(Ingredient.of(itemstack), slots.get(10).x, slots.get(10).y);

        NonNullList<Ingredient> nonnulllist = recipe.value().getIngredients();
        Iterator<Ingredient> iterator = nonnulllist.iterator();
        Slot slot = slots.get(9);
        if (slot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(
                        this.getFuelItems().stream().filter(p_280880_ -> p_280880_.isEnabled(this.minecraft.level.enabledFeatures())).map(ItemStack::new)
                );
            }

            this.ghostRecipe.addIngredient(this.fuels, slot.x, slot.y);
        }



        for (int i = 0; i < 9; i++) {
            if (!iterator.hasNext()) {
                return;
            }
            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = slots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }
    }

    protected Set<Item> getFuelItems() {
        return AbstractFurnaceBlockEntity.getFuel().keySet();
    }
}
