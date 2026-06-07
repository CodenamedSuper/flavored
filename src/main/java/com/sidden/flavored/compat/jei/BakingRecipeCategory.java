package com.sidden.flavored.compat.jei;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.BakingRecipe;
import com.sidden.flavored.registry.FlavoredBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

public class BakingRecipeCategory implements IRecipeCategory<BakingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "baking");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/gui/compat/jei/oven.png");

    public static final RecipeType<BakingRecipe> BAKING_RECIPE_RECIPE_TYPE = new RecipeType<>(UID, BakingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public BakingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 118, 82);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, FlavoredBlocks.OVEN.toStack());
    }

    @Override
    public RecipeType<BakingRecipe> getRecipeType() {
        return BAKING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.flavored.baking");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BakingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 15).addIngredients(Ingredient.of(recipe.getResultItem(null)));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 97, 55);

        int x = 0;
        int y = 0;
        int step = 18;
        Vec2 size = new Vec2(3, 3);

        for (int i = 0; i < 9; i++) {
            IRecipeSlotBuilder slot = builder.addSlot(RecipeIngredientRole.INPUT, 1 + step * x, 15 + step * y);
            if (i < recipe.getIngredients().size()) {
                slot.addIngredients(recipe.getIngredients().get(i));
            }
            x++;
            if (x >= size.x) {
                x = 0;
                y++;
            }
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, BakingRecipe recipe, IFocusGroup focuses) {
        int bakeTime = recipe.getBakingTime();
        if (bakeTime <= 0) {
            bakeTime = 200;
        }
        builder.addAnimatedRecipeArrow(bakeTime).setPosition(61, 15);
        builder.addAnimatedRecipeFlame(300).setPosition(97, 38);

        this.addExperience(builder, recipe);
        this.addBakeTime(builder, recipe);
    }

    protected void addExperience(IRecipeExtrasBuilder builder, BakingRecipe recipe) {
        float experience = recipe.getExperience();
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            builder.addText(experienceString, getWidth() - 20, 10)
                    .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.TOP)
                    .setTextAlignment(HorizontalAlignment.RIGHT)
                    .setColor(0xFF808080);
        }
    }

    protected void addBakeTime(IRecipeExtrasBuilder builder, BakingRecipe recipe) {
        int bakeTime = recipe.getBakingTime();
        if (bakeTime <= 0) {
            bakeTime = 200;
        }

        int cookTimeSeconds = bakeTime / 20;
        Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
        builder.addText(timeString, getWidth() - 20, 10)
                .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
                .setTextAlignment(HorizontalAlignment.RIGHT)
                .setTextAlignment(VerticalAlignment.BOTTOM)
                .setColor(0xFF808080);
    }
}
