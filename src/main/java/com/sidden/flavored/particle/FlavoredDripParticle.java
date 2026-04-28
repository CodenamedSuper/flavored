package com.sidden.flavored.particle;

import com.sidden.flavored.registry.FlavoredParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlavoredDripParticle extends TextureSheetParticle {

    private final Fluid type;
    protected boolean isGlowing;

    protected FlavoredDripParticle(ClientLevel level, double x, double y, double z, Fluid type) {
        super(level, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.type = type;
    }

    public static TextureSheetParticle createChocolateHangParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        FlavoredDripParticle dripHangParticle = new FlavoredDripHangParticle(level, x, y, z, Fluids.EMPTY, FlavoredParticles.FALLING_CHOCOLATE.get());
        dripHangParticle.gravity *= 0.01F;
        dripHangParticle.lifetime = 100;
        dripHangParticle.setColor(0.416f, 0.239f, 0.169f);
        return dripHangParticle;
    }

    public static TextureSheetParticle createChocolateFallParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        FlavoredDripParticle dripparticle = new FlavoredFallAndLandParticle(level, x, y, z, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
        dripparticle.gravity = 0.01F;
        dripparticle.setColor(0.416f, 0.239f, 0.169f);
        return dripparticle;
    }

    public static TextureSheetParticle createChocolateLandParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        FlavoredDripParticle dripparticle = new FlavoredDripLandParticle(level, x, y, z, Fluids.EMPTY);
        dripparticle.lifetime = (int)((double)128.0F / (Math.random() * 0.8 + 0.2));
        dripparticle.setColor(0.416f, 0.239f, 0.169f);
        return dripparticle;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    @OnlyIn(Dist.CLIENT)
    static class FlavoredDripHangParticle extends FlavoredDripParticle {
        private final ParticleOptions fallingParticle;

        FlavoredDripHangParticle(ClientLevel level, double x, double y, double z, Fluid type, ParticleOptions fallingParticle) {
            super(level, x, y, z, type);
            this.fallingParticle = fallingParticle;
            this.gravity *= 0.02F;
            this.lifetime = 40;
        }

        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }

        }

        protected void postMoveUpdate() {
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FlavoredDripLandParticle extends FlavoredDripParticle {
        FlavoredDripLandParticle(ClientLevel p_106102_, double p_106103_, double p_106104_, double p_106105_, Fluid p_106106_) {
            super(p_106102_, p_106103_, p_106104_, p_106105_, p_106106_);
            this.lifetime = (int)((double)16.0F / (Math.random() * 0.8 + 0.2));
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FlavoredFallAndLandParticle extends FlavoredFallingParticle {
        protected final ParticleOptions landParticle;

        FlavoredFallAndLandParticle(ClientLevel level, double x, double y, double z, Fluid type, ParticleOptions landParticle) {
            super(level, x, y, z, type);
            this.landParticle = landParticle;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, (double)0.0F, (double)0.0F, (double)0.0F);
            }

        }
    }


    @OnlyIn(Dist.CLIENT)
    static class FlavoredFallingParticle extends FlavoredDripParticle {
        FlavoredFallingParticle(ClientLevel level, double x, double y, double z, Fluid type) {
            this(level, x, y, z, type, (int)((double)64.0F / (Math.random() * 0.8 + 0.2)));
        }

        FlavoredFallingParticle(ClientLevel level, double x, double y, double z, Fluid type, int lifetime) {
            super(level, x, y, z, type);
            this.lifetime = lifetime;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }

        }
    }


}
