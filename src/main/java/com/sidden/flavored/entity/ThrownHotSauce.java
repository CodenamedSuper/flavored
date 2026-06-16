package com.sidden.flavored.entity;

import com.sidden.flavored.registry.FlavoredEntities;
import com.sidden.flavored.registry.FlavoredItems;
import com.sidden.flavored.registry.FlavoredParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class ThrownHotSauce extends ThrowableItemProjectile {

    public static final int BURN_ATTACK_RANGE = 2;

    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);
    private static final EntityDataAccessor<Float> DATA_THROW_POWER;

    public static float DEFAULT_THROW_POWER = 1;

    public ThrownHotSauce(EntityType<? extends ThrownHotSauce> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownHotSauce(Level level, LivingEntity shooter) {
        super(FlavoredEntities.HOT_SAUCE.get(), shooter, level);
    }

    public ThrownHotSauce(Level level, double x, double y, double z) {
        super(FlavoredEntities.HOT_SAUCE.get(), x, y, z, level);

    }

    public void setThrowPower(float throwPower) {
        this.getEntityData().set(DATA_THROW_POWER, throwPower);
    }

    public float getThrowPower() {
        return this.getEntityData().get(DATA_THROW_POWER);
    }


    public void handleEntityEvent(byte id) {
        if (id == 3) {
            double d0 = 0.08;

            int flameAmount = 8;
            int flameBunchAmount = 3;
            int smokeParticle = 6;

            for(int i = 0; i < flameAmount; ++i) {

                int randomX = level().random.nextInt(-1, 1);
                int randomY = 1;
                int randomZ = level().random.nextInt(-1, 1);
                this.level().addParticle(ParticleTypes.FLAME, getX() + randomX, getY() + randomY , getZ() + randomZ, 0.05f* randomX,0.1f * randomY, 0.05f * randomZ);


            }

            for(int i = 0; i < flameBunchAmount; ++i) {

                this.level().addParticle(FlavoredParticles.FLAME_BUNCH.get(), getX(), getY(), getZ(), 0,0.2f, 0);


            }
            for(int i = 0; i < smokeParticle; ++i) {

                int randomX = level().random.nextInt(-1, 1);
                int randomZ = level().random.nextInt(-1, 1);
                this.level().addParticle(ParticleTypes.SMOKE, getX() + randomX, getY(), getZ() + randomZ, 0,0f, 0);


            }
        }

    }

    protected double getDefaultGravity() {
        return 0.05f;
    }


    public List<Entity> getBurntEntities(Level level) {

        BlockPos pos = getOnPos();
        BlockPos start = new BlockPos(pos.getX() - BURN_ATTACK_RANGE, pos.getY(), pos.getZ() - BURN_ATTACK_RANGE);
        BlockPos end = new BlockPos(pos.getX() + BURN_ATTACK_RANGE, pos.getY() + BURN_ATTACK_RANGE, pos.getZ() + BURN_ATTACK_RANGE);

        return  level.getEntities(this, new AABB(start.getCenter(), end.getCenter()));
    }



    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 5F);
        result.getEntity().igniteForSeconds(10);
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {


            this.playSound(SoundEvents.SPLASH_POTION_BREAK);

            for (Entity target : getBurntEntities(level())) {
                if (target instanceof LivingEntity targetLivingEntity) {
                    targetLivingEntity.igniteForSeconds(8);

                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putFloat("throw_power", getThrowPower());
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        setThrowPower(compound.getFloat("throw_power"));
        super.readAdditionalSaveData(compound);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_THROW_POWER, DEFAULT_THROW_POWER);

        super.defineSynchedData(builder);

    }

    protected Item getDefaultItem() {
        return FlavoredItems.HOT_SAUCE.get();
    }

    static {
        DATA_THROW_POWER = SynchedEntityData.defineId(ThrownHotSauce.class, EntityDataSerializers.FLOAT);
    }
}