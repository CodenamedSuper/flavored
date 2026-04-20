package com.sidden.flavored.client.screen;


import com.mojang.blaze3d.systems.RenderSystem;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.block.property.MixingBowlLiquid;
import com.sidden.flavored.client.menu.KegMenu;
import com.sidden.flavored.client.menu.MixingBowlMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MixingBowlScreen extends AbstractContainerScreen<MixingBowlMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/gui/mixing_bowl.png");

    public MixingBowlScreen(MixingBowlMenu menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 5;
        this.titleLabelX = 10;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderLiquid(guiGraphics, x, y);
        renderMixable(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(TEXTURE, x + 80, y + 26, 176, 0, menu.getScaledProgress(), 16);

    }

    private void renderLiquid(GuiGraphics guiGraphics, int x, int y) {
        if (menu.blockEntity.getLiquid() == MixingBowlLiquid.NONE) return;

        if (menu.blockEntity.getLiquid() == MixingBowlLiquid.WATER) {

            guiGraphics.blit(TEXTURE, x + 42, y + 63, 176, 28, 10, 10);

        }
        else if (menu.blockEntity.getLiquid() == MixingBowlLiquid.MILK) {

            guiGraphics.blit(TEXTURE, x + 42, y + 63, 187, 28, 10, 10);

        }
    }

    private void renderMixable(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 86, y + 64, 176, 17, 10, 10);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderMenuBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}