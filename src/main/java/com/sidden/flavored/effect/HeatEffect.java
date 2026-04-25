package com.sidden.flavored.effect;

import com.sidden.flavored.registry.FlavoredEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

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

        if(!livingEntity.fireImmune()) {
            livingEntity.hurt(livingEntity.damageSources().onFire(), 2.0F + amplifier);
        }

    }




    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}