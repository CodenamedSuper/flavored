package com.sidden.flavored.effect;

import com.sidden.flavored.registry.FlavoredEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class HeatEffect extends MobEffect {
    public HeatEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        if (livingEntity.isInWater()) {
            livingEntity.removeEffect(FlavoredEffects.HEAT);
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);

        if (!livingEntity.fireImmune()) {
            float damageAmount = 2.0F + amplifier;

            if (livingEntity instanceof Player player) {
                Random random = new Random(player.getUUID().getLeastSignificantBits());

                float spiceTolerance = 0.5F + (random.nextFloat() * 1.5F);

                damageAmount *= spiceTolerance;
            }

            livingEntity.hurt(livingEntity.damageSources().onFire(), damageAmount);
        }
    }




    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}