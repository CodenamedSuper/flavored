package com.sidden.flavored.compat.jei;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.MixingRecipe;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

public class MixingRecipeCategory implements IRecipeCategory<MixingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "mixing");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID,
            "textures/gui/compat/jei/mixing_bowl.png");

    public static final RecipeType<MixingRecipe> MIXING_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, MixingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public MixingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FlavoredItems.PORRIDGE.get()));
    }

    @Override
    public RecipeType<MixingRecipe> getRecipeType() {
        return MIXING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("categories.flavored.mixing");
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
    public void setRecipe(IRecipeLayoutBuilder builder, MixingRecipe recipe, IFocusGroup focuses) {

        int x = 0;
        int y = 0;
        int step = 18;
        Vec2 size = new Vec2(3, 2);

        for (int i = 0; i < recipe.getIngredients().size(); i++) {

            if (recipe.getIngredients().get(i) == recipe.liquidInput()) {
                builder.addSlot(RecipeIngredientRole.INPUT, 62, 60).addIngredients(recipe.getIngredients().get(recipe.getIngredients().size() - 1));
            } else if (recipe.getIngredients().get(i) == recipe.vesselInput()) {
                builder.addSlot(RecipeIngredientRole.INPUT, 135, 25).addIngredients(recipe.getIngredients().get(recipe.getIngredients().size() - 2));
            } else {
                builder.addSlot(RecipeIngredientRole.INPUT, 44 + step * x, 18 + step * y).addIngredients(recipe.getIngredients().get(i));
                x++;
            }
            if (x >= size.x) {
                x = 0;
                y++;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 106, 60).addIngredients(Ingredient.of(recipe.getResultItem(null)));


    }
}