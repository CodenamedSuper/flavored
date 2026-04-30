package com.sidden.flavored.effect;

import com.sidden.flavored.registry.FlavoredBlockTags;
import com.sidden.flavored.registry.FlavoredBlocks;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;


public class PoppedEffect extends MobEffect {
    public PoppedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }



    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        if ((livingEntity.isOnFire() || livingEntity.getBlockStateOn().is(FlavoredBlockTags.HEAT_SOURCES) && !livingEntity.getBlockStateOn().isAir())) {
            pop(livingEntity, 1);
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    public void pop(LivingEntity entity, int amount) {
        Vec3 vec3 = entity.getDeltaMovement();
        entity.setDeltaMovement(vec3.x, amount, vec3.z);

    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}