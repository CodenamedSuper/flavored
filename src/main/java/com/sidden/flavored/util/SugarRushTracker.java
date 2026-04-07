package com.sidden.flavored.util;

import com.sidden.flavored.registry.FlavoredEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class SugarRushTracker {
    private static final Map<UUID, List<Long>> rushTimes = new HashMap<>();
    private static final long ONE_MINUTE_MS = 60_000;
    private static final int THRESHOLD = 5;

    public static void recordSugarRushUse(Player player) {
        UUID id = player.getUUID();
        long now = System.currentTimeMillis();

        rushTimes.computeIfAbsent(id, k -> new ArrayList<>());

        List<Long> times = rushTimes.get(id);

        times.add(now);

        times.removeIf(t -> now - t > ONE_MINUTE_MS);

        if (times.size() >= THRESHOLD) {

            giveSugarCrave(player);
            times.clear();
        }
    }

    private static void giveSugarCrave(Player player) {
        player.addEffect(new MobEffectInstance(FlavoredEffects.SUGAR_CRAVE, 20 * 60 * 5));
    }
}
