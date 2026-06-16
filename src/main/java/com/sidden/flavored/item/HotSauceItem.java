package com.sidden.flavored.item;

import com.sidden.flavored.entity.ThrownHotSauce;
import com.sidden.flavored.entity.ThrownTomato;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HotSauceItem extends Item {
    private static final int DRINK_DURATION = 40;

    public HotSauceItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {
            int time = this.getUseDuration(stack, livingEntity) - timeCharged;
            if (time >= 5) {
                SoundEvent soundEvent = SoundEvents.SNOWBALL_THROW;
                if (!level.isClientSide) {
                    ThrownHotSauce thrownHotSauce = new ThrownHotSauce(level, player);
                    thrownHotSauce.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, (float) Math.clamp(time, 0, 12) / 10, 1.0F);
                    thrownHotSauce.setItem(stack);
                    thrownHotSauce.setThrowPower((float) time / 25);

                    level.addFreshEntity(thrownHotSauce);
                    level.playSound((Player)null, thrownHotSauce, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.hasInfiniteMaterials()) {
                        player.getInventory().removeItem(stack);
                    }

                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }



    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return DRINK_DURATION;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

}
