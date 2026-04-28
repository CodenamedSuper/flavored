package com.sidden.flavored.block;

import com.sidden.flavored.registry.FlavoredParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChocolateBlock extends Block {
    public ChocolateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockPos above = pos.above();

        if (level.canSeeSky(above) && level.isDay() && !level.isRainingAt(above)) {
            for (int i = 0; i < random.nextInt(1) + 1; i++) {
                this.spawnParticle(level, pos, state.getCollisionShape(level, pos), pos.getY() - 0.1);
            }
        }
    }


    private void spawnParticle(Level level, BlockPos pos, VoxelShape shape, double y) {
        this.spawnFluidParticle(
                level,
                (double)pos.getX() + shape.min(Direction.Axis.X),
                (double)pos.getX() + shape.max(Direction.Axis.X),
                (double)pos.getZ() + shape.min(Direction.Axis.Z),
                (double)pos.getZ() + shape.max(Direction.Axis.Z),
                y
        );
    }

    private void spawnFluidParticle(Level particleData, double x1, double x2, double z1, double z2, double y) {
        particleData.addParticle(
                FlavoredParticles.FALLING_CHOCOLATE.get(),
                Mth.lerp(particleData.random.nextDouble(), x1, x2),
                y,
                Mth.lerp(particleData.random.nextDouble(), z1, z2),
                0.0,
                0.0,
                0.0
        );
    }
}
