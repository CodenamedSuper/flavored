package com.sidden.flavored;

import com.mojang.logging.LogUtils;
import com.sidden.flavored.block.entity.renderer.MixingBowlRenderer;
import com.sidden.flavored.client.screen.KegScreen;
import com.sidden.flavored.client.screen.MixingBowlScreen;
import com.sidden.flavored.client.screen.OvenScreen;
import com.sidden.flavored.client.entity.renderer.ChockenRenderer;
import com.sidden.flavored.particle.CheeseAgingParticle;
import com.sidden.flavored.particle.FermentationBubblesParticle;
import com.sidden.flavored.particle.FlavoredDripParticle;
import com.sidden.flavored.registry.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Flavored.MOD_ID)
public class Flavored
{
    public static final String MOD_ID = "flavored";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Flavored(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        FlavoredItems.init(modEventBus);
        FlavoredBlocks.init(modEventBus);
        FlavoredBlockEntities.init(modEventBus);
        FlavoredEffects.init(modEventBus);
        FlavoredEntities.init(modEventBus);
        FlavoredRecipeTypes.init(modEventBus);
        FlavoredMenus.init(modEventBus);
        FlavoredParticles.init(modEventBus);
        FlavoredFeatures.init(modEventBus);
        FlavoredCreativeTabs.init(modEventBus);
        FlavoredSoundEvents.init(modEventBus);
        FlavoredDataAttachments.init(modEventBus);
        FlavoredStats.init(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(FlavoredEntities.CHOCKEN.get(), ChockenRenderer::new);

        }
        @SubscribeEvent
        public static void registerMenuScreens(RegisterMenuScreensEvent event) {
            event.register(FlavoredMenus.KEG.get(), KegScreen::new);
            event.register(FlavoredMenus.MIXING_BOWL.get(), MixingBowlScreen::new);
            event.register(FlavoredMenus.OVEN.get(), OvenScreen::new);

        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    FlavoredBlockEntities.MIXING_BOWL.get(), MixingBowlRenderer::new
            );
        }


        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(FlavoredParticles.CHEESE_AGING.get(), CheeseAgingParticle.Provider::new);
            event.registerSpriteSet(FlavoredParticles.FERMENTATION_BUBBLES.get(), FermentationBubblesParticle.Provider::new);

            event.registerSprite(FlavoredParticles.DRIPPING_CHOCOLATE.get(), FlavoredDripParticle::createChocolateHangParticle);
            event.registerSprite(FlavoredParticles.FALLING_CHOCOLATE.get(), FlavoredDripParticle::createChocolateFallParticle);
            event.registerSprite(FlavoredParticles.LANDING_CHOCOLATE.get(), FlavoredDripParticle::createChocolateLandParticle);
        }
    }


}