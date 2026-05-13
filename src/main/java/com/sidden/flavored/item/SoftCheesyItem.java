package com.sidden.flavored.item;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SoftCheesyItem extends Item {
    public SoftCheesyItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack result = super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide) {

            for(MobEffectInstance effect : livingEntity.getActiveEffects()) {
                if (effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL && livingEntity.getRandom().nextInt(5) == 0 ) {
                    livingEntity.removeEffect(effect.getEffect());
                }
            }
        }

        return result;
    }

}
