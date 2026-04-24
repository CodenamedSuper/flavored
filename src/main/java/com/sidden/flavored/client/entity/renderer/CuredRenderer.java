package com.sidden.flavored.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.client.entity.model.CuredModel;
import com.sidden.flavored.entity.Cured;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CuredRenderer extends AbstractZombieRenderer<Cured, CuredModel<Cured>> {
    private static final ResourceLocation CURED_LOCATION = ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "textures/entity/cured.png");

    public CuredRenderer(EntityRendererProvider.Context p_173964_) {
        super(p_173964_, new CuredModel(p_173964_.bakeLayer(ModelLayers.ZOMBIE)), new CuredModel(p_173964_.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new CuredModel(p_173964_.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)));
    }

    public ResourceLocation getTextureLocation(Cured entity) {
        return CURED_LOCATION;
    }
}