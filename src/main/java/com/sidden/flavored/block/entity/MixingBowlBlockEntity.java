package com.sidden.flavored.block.entity;

import com.sidden.flavored.block.MixingBowlBlock;
import com.sidden.flavored.block.property.MixingBowlLiquid;
import com.sidden.flavored.client.menu.KegMenu;
import com.sidden.flavored.client.menu.MixingBowlMenu;
import com.sidden.flavored.client.recipe.FermentingRecipe;
import com.sidden.flavored.client.recipe.FermentingRecipeInput;
import com.sidden.flavored.client.recipe.MixingRecipe;
import com.sidden.flavored.client.recipe.MixingRecipeInput;
import com.sidden.flavored.itemhandler.KegItemHandler;
import com.sidden.flavored.registry.FlavoredBlockEntities;
import com.sidden.flavored.registry.FlavoredRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MixingBowlBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(8);


    private static final int[] INPUT_SLOTS = new int[6];
    private static final int VESSEL_SLOT = 6;
    private static final int OUTPUT_SLOT = 7;


    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 10;



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

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("mixing_bowl.progress");
    }

    public void mix(int amount) {
        if (hasRecipe()) {
            increaseMixingProgress();
        }

        if (hasProgressFinished()) {
            mixItem();
            resetProgress();
            getLevel().playSound((Player)null, getBlockPos(), SoundEvents.COMPOSTER_FILL_SUCCESS, SoundSource.BLOCKS, 1.0F, 1.0F);

        }
    }


    private void resetProgress() {
        progress = 0;
    }

    private void mixItem() {
        Optional<RecipeHolder<MixingRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) return;

        MixingRecipe recipe = recipeOpt.get().value();
        ItemStack output = recipe.output();

        for (int i = 0; i < INPUT_SLOTS.length; i++) {
            itemHandler.extractItem(i, 1, false);
        }

        if (!recipe.vesselInput().isEmpty()) {
            itemHandler.extractItem(VESSEL_SLOT, 1, false);
        }

        ItemStack currentOutput = itemHandler.getStackInSlot(OUTPUT_SLOT);
        int newCount = currentOutput.getCount() + output.getCount();

        this.itemHandler.setStackInSlot(
                OUTPUT_SLOT,
                new ItemStack(output.getItem(), newCount)
        );

        getLevel().playSound(null, getBlockPos(),
                SoundEvents.COMPOSTER_FILL,
                SoundSource.BLOCKS,
                1.0F, 1.0F);
    }

    public boolean hasRecipe() {
        Optional<RecipeHolder<MixingRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResultItem(null);
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output.getItem());
    }

    public Optional<RecipeHolder<MixingRecipe>> getCurrentRecipe() {

        List<ItemStack> ingredients = new ArrayList<>();

        for (int i = 0; i < INPUT_SLOTS.length; i++) {
            ingredients.add(itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(FlavoredRecipes.MIXING_BOWL_TYPE.get(), new MixingRecipeInput(ingredients, itemHandler.getStackInSlot(VESSEL_SLOT),level.getBlockState(getBlockPos()).getValue(MixingBowlBlock.LIQUID)), level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseMixingProgress() {
        progress++;
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