package com.sidden.flavored.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.registry.FlavoredEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


/**
 * Credits to squeek502 (AppleSkin) and Vectorwing (Farmer's Delight) for the implementation reference!
 * <a href="https://www.curseforge.com/minecraft/mc-mods/appleskin">...</a>
 * <a href="https://www.curseforge.com/minecraft/mc-mods/farmers-delight">...</a>
 */

public class HudOverlays
{
    public static int foodIconsOffset;
    private static final ResourceLocation MOD_ICONS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/gui/flavored_icons.png");

    public static void register(RegisterGuiLayersEvent event) {
        event.registerBelow(
                VanillaGuiLayers.FOOD_LEVEL,
                ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "food_offset"),
                (guiGraphics, deltaTracker) -> foodIconsOffset = Minecraft.getInstance().gui.rightHeight
        );
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, SugarCraveOverlay.ID, new SugarCraveOverlay());
    }

    public static abstract class BaseOverlay implements LayeredDraw.Layer
    {
        public abstract void render(Minecraft mc, Player player, GuiGraphics guiGraphics, int left, int right, int top, int guiTicks);

        @Override
        public final void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.player == null || !shouldRenderOverlay(minecraft, minecraft.player, guiGraphics, minecraft.gui.getGuiTicks()))
                return;

            int top = guiGraphics.guiHeight();
            int left = guiGraphics.guiWidth() / 2 - 91; // left of health bar
            int right = guiGraphics.guiWidth() / 2 + 91; // right of food bar

            render(minecraft, minecraft.player, guiGraphics, left, right, top, minecraft.gui.getGuiTicks());
        }

        public boolean shouldRenderOverlay(Minecraft minecraft, Player player, GuiGraphics guiGraphics, int guiTicks) {
            return !minecraft.options.hideGui && minecraft.gameMode != null && minecraft.gameMode.canHurtPlayer();
        }
    }

    public static class SugarCraveOverlay extends BaseOverlay
    {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "sugar_crave");

        @Override
        public void render(Minecraft minecraft, Player player, GuiGraphics guiGraphics, int left, int right, int top, int guiTicks) {
            FoodData stats = player.getFoodData();

            boolean isPlayerHealingWithSaturation =
                    player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                            && player.isHurt()
                            && stats.getFoodLevel() >= 18;

            if (player.getEffect(FlavoredEffects.SUGAR_CRAVE) != null) {
                drawSugarCraveOverlay(stats, minecraft, guiGraphics, right, top - foodIconsOffset, isPlayerHealingWithSaturation);
            }
        }

    }

    public static void drawSugarCraveOverlay(FoodData foodData, Minecraft minecraft, GuiGraphics graphics, int right, int top, boolean naturalHealing) {
        float saturation = foodData.getSaturationLevel();
        int foodLevel = foodData.getFoodLevel();
        int ticks = minecraft.gui.getGuiTicks();
        Random rand = new Random();
        rand.setSeed(ticks * 312871);

        RenderSystem.enableBlend();


        for (int j = 0; j < 10; ++j) {
            int x = right - j * 8 - 9;
            int y = top;

            if (saturation <= 0.0F && ticks % (foodLevel * 3 + 1) == 0) {
                y = top + (rand.nextInt(3) - 1);
            }

            // Background texture
            graphics.blit(MOD_ICONS_TEXTURE, x, y, 0, 0, 9, 9);

            float effectiveHungerOfBar = (foodData.getFoodLevel()) / 2.0F - j;
            int naturalHealingOffset = naturalHealing ? 18 : 0;

            // Gilded hunger icons
            if (effectiveHungerOfBar >= 1)
                graphics.blit(MOD_ICONS_TEXTURE, x, y, 18 + naturalHealingOffset, 0, 9, 9);
            else if (effectiveHungerOfBar >= .5)
                graphics.blit(MOD_ICONS_TEXTURE, x, y, 9 + naturalHealingOffset, 0, 9, 9);
        }

        RenderSystem.disableBlend();
    }
}