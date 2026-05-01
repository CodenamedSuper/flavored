package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.util.ChocolateAddictionManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = Flavored.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class FlavoredGameBusEvents {

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
            ChocolateAddictionManager.increaseAddiction(player);
        }
    }

    @SubscribeEvent
    public static void onEntityHurt(AttackEntityEvent event) {
        if (event.getEntity().hasEffect(FlavoredEffects.HEAT)) {
            event.getTarget().igniteForSeconds(3f);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        ChocolateAddictionManager.tickAddiction(event.getEntity());
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 16),
                new ItemStack(FlavoredItems.CINNAMON.get(), 8), 1, 10, 0.2f));

        rareTrades.add((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 1),
                new ItemStack(FlavoredBlocks.CINNAMON_SPROUT.get(), 1), 1, 10, 0.2f));
    }

    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event) {

        if (event.getEffectInstance().is(FlavoredEffects.SUGAR_CRAVE) && event.getCure() != null && event.getCure().name().equals(EffectCures.MILK.name())) {
            event.setCanceled(true);
        }
    }

}
