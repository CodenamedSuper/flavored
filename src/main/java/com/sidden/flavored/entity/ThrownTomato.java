package com.sidden.flavored.entity;

import com.sidden.flavored.registry.FlavoredEntities;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownTomato extends ThrowableItemProjectile {
    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public ThrownTomato(EntityType<? extends ThrownTomato> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownTomato(Level level, LivingEntity shooter) {
        super(FlavoredEntities.TOMATO.get(), shooter, level);
    }

    public ThrownTomato(Level level, double x, double y, double z) {
        super(FlavoredEntities.TOMATO.get(), x, y, z, level);
    }

    public void handleEntityEvent(byte id) {
        if (id == 3) {
            double d0 = 0.08;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08);
            }
        }

    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return Items.EGG;
    }
}