package com.sidden.flavored.item;

import com.sidden.flavored.registry.FlavoredBlocks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CuredAppleItem extends Item {
    public CuredAppleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack result = super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide) {

            livingEntity.hurt(livingEntity.damageSources().magic(), 3);

            for(MobEffectInstance effect : livingEntity.getActiveEffects()) {
                if (effect.is((MobEffects.POISON))) {
                    livingEntity.removeEffect(effect.getEffect());
                }
            }
        }

        return result;
    }
}
