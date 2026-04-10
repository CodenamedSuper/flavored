package com.sidden.flavored.mixin;

import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ZombieVillager.class)
public abstract class ZombieVillagerMixin {

    @Shadow protected abstract void startConverting(@Nullable UUID conversionStarter, int villagerConversionTime);

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void onMobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ZombieVillager zombieVillager = (ZombieVillager) (Object) this;
        ItemStack itemstack = player.getItemInHand(hand);

        if (zombieVillager.isConverting()) cir.setReturnValue(InteractionResult.FAIL);

        if (itemstack.is(Items.GOLDEN_APPLE)) {
            cir.setReturnValue(InteractionResult.FAIL);
        }

        if (itemstack.is(FlavoredItems.CURED_APPLE)) {
            itemstack.consume(1, player);

            if (!zombieVillager.level().isClientSide) {
                this.startConverting(player.getUUID(), zombieVillager.getRandom().nextInt(2401) + 3600);
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
        }


    }
}