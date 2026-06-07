package com.sidden.flavored.client.screen;


import com.mojang.blaze3d.systems.RenderSystem;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.menu.MixingBowlMenu;
import com.sidden.flavored.recipe.recipe_book.MixingRecipeBookComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class MixingBowlScreen extends AbstractContainerScreen<MixingBowlMenu> implements RecipeUpdateListener {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/gui/mixing_bowl.png");

    private final MixingRecipeBookComponent recipeBookComponent;
    private boolean widthTooNarrow;


    public MixingBowlScreen(MixingBowlMenu menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle);
        this.recipeBookComponent = new MixingRecipeBookComponent();
    }


    @Override
    protected void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;

        this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, p_313431_ -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            p_313431_.setPosition(this.leftPos + 5, this.height / 2 - 49);
        }));
        this.inventoryLabelY = 10000;
        this.titleLabelY = 5;
        this.titleLabelX = 10;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.recipeBookComponent.tick();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = this.recipeBookComponent.isVisible() ? ((width - imageWidth) / 2) + 77 : (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);}

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(TEXTURE, x + 80, y + 26, 176, 0, menu.getScaledProgress(), 16);

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBackground(guiGraphics, mouseX, mouseY, delta);
            this.recipeBookComponent.render(guiGraphics, mouseX, mouseY, delta);
        } else {
            super.render(guiGraphics, mouseX, mouseY, delta);
            this.recipeBookComponent.render(guiGraphics, mouseX, mouseY, delta);
            this.recipeBookComponent.renderGhostRecipe(guiGraphics, this.leftPos, this.topPos, true, delta);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.recipeBookComponent.renderTooltip(guiGraphics, this.leftPos, this.topPos, mouseX, mouseY);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookComponent.isVisible() ? true : super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        this.recipeBookComponent.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean flag = mouseX < (double) guiLeft
                || mouseY < (double) guiTop
                || mouseX >= (double) (guiLeft + this.imageWidth)
                || mouseY >= (double) (guiTop + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return this.recipeBookComponent.charTyped(codePoint, modifiers) ? true : super.charTyped(codePoint, modifiers);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }

}