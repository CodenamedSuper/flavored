package com.sidden.flavored.block.entity.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.block.entity.MixingBowlBlockEntity;
import com.sidden.flavored.data.MixingBowlData;
import com.sidden.flavored.registry.FlavoredDataMapTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;

@OnlyIn(Dist.CLIENT)
public class MixingBowlRenderer implements BlockEntityRenderer<MixingBowlBlockEntity> {

    private final ItemRenderer itemRenderer;
    private final ModelPart bone;
    public ResourceLocation texture;
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Flavored.MOD_ID, "mixing_bowl_liquid"), "main");

    public MixingBowlRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.bone = modelPart.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-10, 0).addBox(-5, -19, 11, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void render(MixingBowlBlockEntity blockEntity, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        ItemStackHandler inventory = blockEntity.getInventory();
        int index = 0;
        ItemStack itemStack = blockEntity.getInventory().getStackInSlot(7);
        Holder<Item> holder = itemStack.getItemHolder();
        MixingBowlData data = holder.getData(FlavoredDataMapTypes.MIXING_BOWL_DATA_MAP);
        if (data != null) {
            texture = data.textureLocation();
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
            bone.render(poseStack, vertexConsumer, packedLight+10, packedOverlay);
        }


        for (int i = 0; i < 6; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            poseStack.pushPose();
            poseStack.translate(0.5, 0.2 + (index * 0.08), 0.5);

            int wiggle = blockEntity.wiggleTime;
            if (wiggle > 0) {
                float time = wiggle - partialTick;

                float wiggleOffset = (float) Math.sin(time * 2.0f) * 0.05f;
                float wiggleRot = (float) Math.sin(time * 3.0f) * 10f;
                float bounce = (float) Math.sin(time * 4.0f) * 0.03f;

                poseStack.translate(wiggleOffset, bounce, -wiggleOffset);
                poseStack.mulPose(Axis.YP.rotationDegrees(wiggleRot));

            }

            poseStack.mulPose(Axis.XP.rotationDegrees(90));

            poseStack.scale(0.5f, 0.5f, 0.5f);

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

            poseStack.popPose();
            index++;
        }
    }
}