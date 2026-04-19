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

        ItemStackHandler inv = blockEntity.getInventory();

        int rendered = 0;

        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            poseStack.pushPose();

            poseStack.translate(0.5, 0.2 + (rendered * 0.1), 0.5);

            poseStack.mulPose(Axis.XP.rotationDegrees(90));

            poseStack.scale(0.6f, 0.6f, 0.6f);

            itemRenderer.renderStatic(
                    stack,
                    ItemDisplayContext.FIXED,
                    packedLight,
                    packedOverlay,
                    poseStack,
                    bufferSource,
                    blockEntity.getLevel(),
                    0
            );

            poseStack.popPose();

            rendered++;
        }
    }
}