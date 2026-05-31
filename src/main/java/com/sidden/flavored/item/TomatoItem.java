package com.sidden.flavored.item;

import com.sidden.flavored.entity.ThrownTomato;
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

public class TomatoItem extends Item {
    public TomatoItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (player.isCrouching()) {

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!level.isClientSide) {
                ThrownTomato thrownTomato = new ThrownTomato(level, player);
                thrownTomato.setItem(itemstack);
                thrownTomato.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(thrownTomato);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            itemstack.consume(1, player);
        }


        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        ThrownTomato thrownTomato = new ThrownTomato(level, pos.x(), pos.y(), pos.z());
        thrownTomato.setItem(stack);
        return thrownTomato;
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
