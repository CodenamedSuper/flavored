package com.sidden.flavored.particle;

import com.sidden.flavored.particle.type.option.FlavoredColorParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class FermentationBubblesParticle extends TextureSheetParticle {
    protected FermentationBubblesParticle(ClientLevel level, double x, double y, double z, FlavoredColorParticleOption options, SpriteSet spriteSet,
                                          double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.8f;

        this.lifetime = level.getRandom().nextInt(25, 35);
        this.setSpriteFromAge(spriteSet);

        this.scale(1.5f);
        int color = options.color();
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        this.setColor(r, g, b);

    }

    @Override
    public void tick() {
        System.out.println("Particle tick");
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<FlavoredColorParticleOption> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }

        @Nullable
        @Override
        public Particle createParticle(FlavoredColorParticleOption options, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            FermentationBubblesParticle particle = new FermentationBubblesParticle(level, x, y, z, options, sprites, dx, dy, dz);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}