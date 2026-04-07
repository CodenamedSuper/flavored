package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.util.SugarRushTracker;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Flavored.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class FlavoredGameEvents {

    @SubscribeEvent
    public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (player.hasEffect(FlavoredEffects.SUGAR_CRAVE)) {
            if (!stack.is(FlavoredItemTags.CHOCOLATY) && stack.has(DataComponents.FOOD)) {
                event.setCanceled(true);
                player.displayClientMessage(
                        Component.literal("You crave only chocolaty foods right now..."),
                        true
                );
            }
        }
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect() == FlavoredEffects.SUGAR_RUSH && event.getEntity() instanceof Player player) {
            SugarRushTracker.recordSugarRushUse(player);
        }
    }
}
