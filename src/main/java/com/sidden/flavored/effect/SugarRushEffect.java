package com.sidden.flavored.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SugarRushEffect extends MobEffect {
    public SugarRushEffect(MobEffectCategory category, int color) {
        super(category, color);
    }



    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {



        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}