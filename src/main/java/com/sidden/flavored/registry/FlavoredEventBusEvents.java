package com.sidden.flavored.registry;


import com.sidden.flavored.Flavored;
import com.sidden.flavored.client.hud.HudOverlays;
import com.sidden.flavored.entity.Chocken;
import com.sidden.flavored.client.entity.model.ChockenModel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Flavored.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class FlavoredEventBusEvents {

    @SubscribeEvent
    public  static  void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChockenModel.LAYER_LOCATION, ChockenModel::createBodyLayer);

    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        HudOverlays.register(event);
    }


    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FlavoredEntities.CHOCOLATE_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(FlavoredEntities.TOMATO.get(), ThrownItemRenderer::new);

    }

    @SubscribeEvent
    public  static  void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(FlavoredEntities.CHOCKEN.get(), Chocken.createAttributes().build());


    }

}

