package com.sidden.flavored.effect;

import com.sidden.flavored.registry.FlavoredEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;
import java.util.UUID;

public class HeatEffect extends MobEffect {

    public static final int MAX_DAMAGE = 9;

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
            float damage = (float) amplifier;

            if (livingEntity instanceof Player player) {
                Random seed = new Random(UUID.fromString("3186d0b6-8633-46a1-a429-603d4f0ffe7a").getLeastSignificantBits());
                float spiceTolerance = seed.nextFloat() * MAX_DAMAGE;

                float variance = (livingEntity.getRandom().nextFloat() * 2.0F) - 1.0F;

                damage = spiceTolerance + variance;


            } else {
                damage += 2.0F;
            }

            livingEntity.hurt(livingEntity.damageSources().onFire(), Math.max(0, damage));
        }
    }





    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}