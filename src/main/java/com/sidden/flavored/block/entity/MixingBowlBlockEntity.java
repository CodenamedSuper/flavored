package com.sidden.flavored.block.entity;

import com.sidden.flavored.block.MixingBowlBlock;
import com.sidden.flavored.block.property.MixingBowlLiquid;
import com.sidden.flavored.menu.MixingBowlMenu;
import com.sidden.flavored.recipe.MixingRecipe;
import com.sidden.flavored.recipe.input.MixingRecipeInput;
import com.sidden.flavored.registry.FlavoredBlockEntities;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import com.sidden.flavored.registry.FlavoredStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MixingBowlBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();

            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int[] INPUT_SLOTS = new int[6];
    private static final int VESSEL_SLOT = 6;


    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 10;

    public int wiggleTime = 0;




    public MixingBowlBlockEntity(BlockPos pos, BlockState blockState) {
        super(FlavoredBlockEntities.MIXING_BOWL.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> MixingBowlBlockEntity.this.progress;
                    case 1 -> MixingBowlBlockEntity.this.maxProgress;
                    case 2 -> MixingBowlBlockEntity.this.hasRecipe() ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> MixingBowlBlockEntity.this.progress = pValue;
                    case 1 -> MixingBowlBlockEntity.this.maxProgress = pValue;

                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

    }



    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public Component getDisplayName() {
        return Component.translatable("block.flavored.mixing_bowl");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {

        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("mixing_bowl.progress", progress);
        tag.putInt("wiggle_time", wiggleTime);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("mixing_bowl.progress");
        wiggleTime = tag.getInt("wiggle_time");
    }


    public void mix(int amount, Player player) {
        if (hasRecipe()) {
            increaseMixingProgress(amount);
            wiggleTime = 5;
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }

        if (hasProgressFinished()) {
            mixItem();
            player.awardStat(FlavoredStats.MIX_ITEM.value());
            resetProgress();
        }
    }


    public void resetProgress() {
        progress = 0;
        setChanged();
    }
    private void mixItem() {
        Optional<RecipeHolder<MixingRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) return;

        MixingRecipe recipe = recipeOpt.get().value();
        ItemStack output = recipe.output().copy();

        Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), output.copy());

        for (int i = 0; i < INPUT_SLOTS.length; i++) {
            itemHandler.extractItem(i, 1, false);
        }

        if (!recipe.vesselInput().isEmpty()) {
            itemHandler.extractItem(VESSEL_SLOT, 1, false);
        }

        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

        getLevel().playSound(null, getBlockPos(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
    }

    public void tick(Level level, BlockState state, BlockPos pos) {

        if (!level.isClientSide) {
            if (!hasRecipe()) {
                resetProgress();
            }
        }

        if (wiggleTime > 0) {
            wiggleTime--;
        }
    }

    public boolean hasRecipe() {
        Optional<RecipeHolder<MixingRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        loadAdditional(tag, registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public Optional<RecipeHolder<MixingRecipe>> getCurrentRecipe() {

        List<ItemStack> ingredients = new ArrayList<>();

        for (int i = 0; i < INPUT_SLOTS.length; i++) {
            ingredients.add(itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(FlavoredRecipeTypes.MIXING_BOWL_TYPE.get(), new MixingRecipeInput(ingredients, itemHandler.getStackInSlot(VESSEL_SLOT),level.getBlockState(getBlockPos()).getValue(MixingBowlBlock.LIQUID)), level);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseMixingProgress(int amount) {
        progress += amount;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MixingBowlMenu(containerId, playerInventory, this, this.data);
    }

    public  ItemStackHandler getInventory() {
        return  this.itemHandler;
    }

    public MixingBowlLiquid getLiquid() {
        return level.getBlockState(getBlockPos()).getValue(MixingBowlBlock.LIQUID);
    }


}