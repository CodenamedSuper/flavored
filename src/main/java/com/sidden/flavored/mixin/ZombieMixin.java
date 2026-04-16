package com.sidden.flavored.mixin;

import com.sidden.flavored.util.ZombieConversionAccess;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Zombie.class)
public abstract class ZombieMixin extends Monster implements ZombieConversionAccess {

    protected ZombieMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Unique
    private static final EntityDataAccessor<Boolean> DATA_CONVERTING =
            SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);

    @Unique private int conversionTime;
    @Unique private UUID conversionStarter;

    // register data
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_CONVERTING, false);
    }

    @Override
    public boolean isConverting() {
        return this.entityData.get(DATA_CONVERTING);
    }

    @Override
    public void startConversion(UUID player, int time) {
        this.conversionStarter = player;
        this.conversionTime = time;

        this.entityData.set(DATA_CONVERTING, true);

        this.removeEffect(MobEffects.WEAKNESS);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, time));

        this.level().broadcastEntityEvent(this, (byte) 16);
    }

    // ticking logic
    @Inject(method = "tick", at = @At("HEAD"))
    private void tickConversion(CallbackInfo ci) {
        if (!this.level().isClientSide && this.isAlive() && this.isConverting()) {

            this.conversionTime--;

            if (this.conversionTime <= 0) {
                finishConversion((ServerLevel) this.level());
            }
        }
    }

    @Unique
    private void finishConversion(ServerLevel level) {
        Chicken chicken = ((Zombie)(Object)this).convertTo(EntityType.CHICKEN, false);

        if (chicken != null) {
            chicken.finalizeSpawn(
                    level,
                    level.getCurrentDifficultyAt(chicken.blockPosition()),
                    MobSpawnType.CONVERSION,
                    null
            );

            chicken.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));

            if (!this.isSilent()) {
                level.levelEvent(null, 1027, this.blockPosition(), 0);
            }
        }
    }
}