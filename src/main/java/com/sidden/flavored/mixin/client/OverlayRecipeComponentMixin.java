package com.sidden.flavored.mixin.client;

import com.sidden.flavored.menu.KegMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverlayRecipeComponent.class)
public class OverlayRecipeComponentMixin {
    @Shadow
    boolean isFurnaceMenu;

    @Inject(method = "init", at = @At("HEAD"))
    private void setOverlayStyle(Minecraft minecraft, RecipeCollection collection, int x, int y, int p_100199_, int p_100200_, float p_100201_, CallbackInfo ci) {
        if (minecraft.player.containerMenu instanceof KegMenu) {
            this.isFurnaceMenu = true;
        }
    }
}
