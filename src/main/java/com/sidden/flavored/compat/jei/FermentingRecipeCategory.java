package com.sidden.flavored.compat.jei;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.FermentingRecipe;
import com.sidden.flavored.registry.FlavoredBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
        this.background = helper.createDrawable(TEXTURE, 0, 0, 109, 26);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, FlavoredBlocks.KEG.toStack());
    }

    @Override
    public RecipeType<FermentingRecipe> getRecipeType() {
        return FERMENTING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.flavored.fermenting");
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
        builder.addSlot(RecipeIngredientRole.INPUT, 28, 5).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 5).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 5).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, FermentingRecipe recipe, IFocusGroup focuses) {
        builder.addAnimatedRecipeArrow(1248).setPosition(52, 4);
    }
}