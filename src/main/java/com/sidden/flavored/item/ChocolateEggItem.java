package com.sidden.flavored.item;

import com.sidden.flavored.entity.ThrownChocolateEgg;
import com.sidden.flavored.entity.ThrownChocolateEgg;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class ChocolateEggItem extends Item implements ProjectileItem {
    public ChocolateEggItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (player.isCrouching() && player.canEat(false)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundSource.PLAYERS,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide) {
            ThrownChocolateEgg thrownChocolateEgg = new ThrownChocolateEgg(level, player);
            thrownChocolateEgg.setItem(itemstack);
            thrownChocolateEgg.shootFromRotation(player, player.getXRot(), player.getYRot(),
                    0.0F, 1.5F, 1.0F);
            level.addFreshEntity(thrownChocolateEgg);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, player);

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownChocolateEgg thrownChocolateEgg = new ThrownChocolateEgg(level, pos.x(), pos.y(), pos.z());
        thrownChocolateEgg.setItem(stack);
        return thrownChocolateEgg;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return  32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
}