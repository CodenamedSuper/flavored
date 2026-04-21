package com.sidden.flavored.block.entity.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sidden.flavored.block.entity.MixingBowlBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MixingBowlRenderer implements BlockEntityRenderer<MixingBowlBlockEntity> {

    private final ItemRenderer itemRenderer;

    public MixingBowlRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(MixingBowlBlockEntity blockEntity, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource,
                       int packedLight, int packedOverlay) {

        ItemStackHandler inventory = blockEntity.getInventory();
        int index = 0;

        for (int i = 0; i < 6; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            poseStack.pushPose();

            RandomSource rand = RandomSource.create(blockEntity.getBlockPos().asLong() + i);


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