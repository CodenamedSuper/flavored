package com.sidden.flavored.block;

import com.sidden.flavored.registry.FlavoredParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChocolateSlabBlock extends SlabBlock {
    public ChocolateSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockPos above = pos.above();
        long timeOfDay = level.getDayTime() % 24000;

        boolean isNightTime = timeOfDay >= 13000 && timeOfDay <= 23000;

        if (isNightTime) return;


        if (level.canSeeSky(above) && !level.isRainingAt(above)) {
            for (int i = 0; i < random.nextInt(1) + 1; i++) {
                if (random.nextInt(35) == 0) this.spawnParticle(level, pos, state.getCollisionShape(level, pos), pos.getY() - 0.1);
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
