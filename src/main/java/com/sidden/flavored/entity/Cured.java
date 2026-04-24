package com.sidden.flavored.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class Cured extends Zombie {
    public Cured(EntityType<? extends Cured> entityType, Level level) {
        super(entityType, level);
    }
}
