package com.sidden.flavored.compat.jei;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.FermentingRecipe;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FermentingRecipeCategory implements IRecipeCategory<FermentingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "fermenting");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID,
            "textures/gui/compat/jei/keg.png");

    public static final RecipeType<FermentingRecipe> FERMENTING_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, FermentingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FermentingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FlavoredItems.SWEET_BERRY_WINE.get()));
    }

    @Override
    public RecipeType<FermentingRecipe> getRecipeType() {
        return FERMENTING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("category.flavored.fermenting");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FermentingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 29, 35).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(recipe.getResultItem(null));
    }
}