package com.sidden.flavored.block.entity;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.menu.MixingBowlMenu;
import com.sidden.flavored.recipe.MixingRecipe;
import com.sidden.flavored.recipe.input.MixingRecipeInput;
import com.sidden.flavored.registry.FlavoredBlockEntities;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import com.sidden.flavored.registry.FlavoredStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MixingBowlBlockEntity extends BaseContainerBlockEntity implements StackedContentsCompatible {
    public static final int[] INGREDIENT_SLOTS = new int[]{ 0, 1, 2, 3, 4, 5 };
    public static final int VESSEL_SLOT = 6;
    public static final int LIQUID_SLOT = 7;
    public static final int MAX_MIX_PROGRESS = 10;

    protected final ContainerData dataAccess;

    private NonNullList<ItemStack> items;
    private int mixProgress = 0;
    public int wiggleTime = 0;

    public MixingBowlBlockEntity(BlockPos pos, BlockState blockState) {
        super(FlavoredBlockEntities.MIXING_BOWL.get(), pos, blockState);

        this.items = NonNullList.withSize(8, ItemStack.EMPTY);
        this.dataAccess = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MixingBowlBlockEntity.this.mixProgress;
                    case 1 -> MixingBowlBlockEntity.this.hasValidRecipe() ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MixingBowlBlockEntity.this.mixProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);

        this.mixProgress = tag.getInt("mix_progress");
        this.wiggleTime = tag.getInt("wiggle_time");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ContainerHelper.saveAllItems(tag, this.items, true, registries);
        tag.putInt("mix_progress", mixProgress);
        tag.putInt("wiggle_time", wiggleTime);
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for (ItemStack stack : this.items) {
            contents.accountStack(stack);
        }
    }

    public void mix(int amount, Player player) {
        if (hasValidRecipe()) {
            increaseMixingProgress(amount);
            wiggleTime = 5;

            this.setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }

        if (hasProgressFinished()) {
            mixItem();
            player.awardStat(FlavoredStats.MIX_ITEM.value());
            resetProgress();
        }
    }

    public void resetProgress() {
        this.mixProgress = 0;
        this.setChanged();
    }

    private void mixItem() {
        Optional<RecipeHolder<MixingRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) return;

        MixingRecipe recipe = recipeOpt.get().value();
        ItemStack output = recipe.output().copy();

        Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), output.copy());

        for (int i = 0; i < INGREDIENT_SLOTS.length; i++) {
            ContainerHelper.removeItem(this.items, i, 1);
        }

        if (!recipe.vesselInput().isEmpty()) {
            ContainerHelper.removeItem(this.items, VESSEL_SLOT, 1);
        }

        this.setChanged();

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        level.playSound(null, getBlockPos(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MixingBowlBlockEntity blockEntity) {
        if (!level.isClientSide) {
            if (!blockEntity.hasValidRecipe()) {
                blockEntity.resetProgress();
            }
            return;
        }

        if (blockEntity.wiggleTime > 0) {
            blockEntity.wiggleTime--;
        }
    }

    public boolean hasValidRecipe() {
        return this.getCurrentRecipe().isPresent();
    }

    public Optional<RecipeHolder<MixingRecipe>> getCurrentRecipe() {
        List<ItemStack> ingredients = new ArrayList<>();

        for (int i = 0; i < INGREDIENT_SLOTS.length; i++) {
            ingredients.add(this.items.get(i));
        }

        RecipeManager recipeManager = this.level.getRecipeManager();
        return recipeManager.getRecipeFor(
                FlavoredRecipeTypes.MIXING_BOWL_TYPE.get(),
                new MixingRecipeInput(
                        ingredients,
                        this.items.get(VESSEL_SLOT),
                        this.items.get(LIQUID_SLOT)
                ),
                level
        );
    }

    private boolean hasProgressFinished() {
        return mixProgress >= MAX_MIX_PROGRESS;
    }

    private void increaseMixingProgress(int amount) {
        mixProgress += amount;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new MixingBowlMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.flavored.mixing_bowl");
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack stackAtIndex = this.items.get(slot);
        boolean sameItem = !stack.isEmpty() && ItemStack.isSameItemSameComponents(stackAtIndex, stack);

        Flavored.LOGGER.info("Set Item");

        this.items.set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));

        if (!sameItem) {
            this.mixProgress = 0;
        }

        this.setChanged();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public int getContainerSize() {
        return 8;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);

        ContainerHelper.saveAllItems(tag, this.items, true, registries);
        tag.putInt("mix_progress", mixProgress);
        tag.putInt("wiggle_time", wiggleTime);

        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}