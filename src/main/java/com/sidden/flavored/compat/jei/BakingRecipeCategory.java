package com.sidden.flavored.compat.jei;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.recipe.BakingRecipe;
import com.sidden.flavored.registry.FlavoredBlocks;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class BakingRecipeCategory implements IRecipeCategory<BakingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "baking");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/gui/compat/jei/oven.png");

    public static final RecipeType<BakingRecipe> BAKING_RECIPE_RECIPE_TYPE = new RecipeType<>(UID, BakingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public BakingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FlavoredBlocks.PIZZA.asItem()));
    }

    @Override
    public RecipeType<BakingRecipe> getRecipeType() {
        return BAKING_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("category.flavored.baking");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    protected Set<Item> getFuelItems() {
        return AbstractFurnaceBlockEntity.getFuel().keySet();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BakingRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 17).addIngredients(Ingredient.of(recipe.getResultItem(null)));

        int x = 0;
        int y = 0;
        int step = 18;
        Vec2 size = new Vec2(3, 3);

        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 20 + step * x, 17 + step * y).addIngredients(recipe.getIngredients().get(i));
            x++;
            if (x >= size.x) {
                x = 0;
                y++;
            }
        }
    }
}