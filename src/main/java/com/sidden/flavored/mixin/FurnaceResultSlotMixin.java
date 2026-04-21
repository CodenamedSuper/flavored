package com.sidden.flavored.mixin;

import com.sidden.flavored.block.entity.OvenBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FurnaceResultSlot.class)
public abstract class FurnaceResultSlotMixin extends Slot {
    @Shadow
    @Final
    private Player player;

    public FurnaceResultSlotMixin(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Inject(method = "checkTakeAchievements", at = @At(value = "FIELD", target = "Lnet/minecraft/world/inventory/FurnaceResultSlot;container:Lnet/minecraft/world/Container;", shift = At.Shift.AFTER))
    private void awardOvenRecipesAndExperience(ItemStack stack, CallbackInfo ci) {
        if (this.container instanceof OvenBlockEntity oven) {
            oven.awardUsedRecipesAndPopExperience((ServerPlayer) this.player);
        }
    }
}
