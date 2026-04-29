package com.sidden.flavored.util;

import com.sidden.flavored.registry.FlavoredDataAttachments;
import com.sidden.flavored.registry.FlavoredEffects;
import com.sidden.flavored.registry.FlavoredStats;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class ChocolateAddictionManager {
    private static final int THRESHOLD = 5;
    private static final int DRAIN_INTERVAL = 20 * 60;

    public static void tickAddiction(Player player) {
        if (player.level().isClientSide) return;

        long lastTick = player.getData(FlavoredDataAttachments.LAST_DRAIN_TICK);
        long currentTick = player.level().getGameTime();

        if (currentTick - lastTick >= DRAIN_INTERVAL) {
            int currentAddiction = player.getData(FlavoredDataAttachments.CHOCOLATE_ADDICTION);

            if (currentAddiction > 0) {
                player.setData(FlavoredDataAttachments.CHOCOLATE_ADDICTION, currentAddiction - 1);
            }

            if (player.hasEffect(FlavoredEffects.SUGAR_CRAVE)) {
                player.awardStat(FlavoredStats.CRAVE_CHOCOLATE.value(), 1);
            }

            player.setData(FlavoredDataAttachments.LAST_DRAIN_TICK, currentTick);
        }
    }

    public static void increaseAddiction(Player player) {
        int current = player.getData(FlavoredDataAttachments.CHOCOLATE_ADDICTION);
        int newValue = current + 1;

        player.setData(FlavoredDataAttachments.CHOCOLATE_ADDICTION, newValue);

        if (newValue >= THRESHOLD) {
            giveSugarCrave(player);
        }
    }

    private static void giveSugarCrave(Player player) {

        player.addEffect(new MobEffectInstance(FlavoredEffects.SUGAR_CRAVE, 20 * 60 * 5));
    }
}
