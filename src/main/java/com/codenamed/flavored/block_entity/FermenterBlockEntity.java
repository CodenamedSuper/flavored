package com.codenamed.flavored.block_entity;

import com.codenamed.flavored.menu.FermenterMenu;
import com.codenamed.flavored.recipe.FermentingRecipe;
import com.codenamed.flavored.recipe.FermentingRecipeInput;
import com.codenamed.flavored.registry.FlavoredBlockEntities;
import com.codenamed.flavored.registry.FlavoredBlocks;
import com.codenamed.flavored.registry.FlavoredItems;
import com.codenamed.flavored.registry.FlavoredRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


public class FermenterBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int FERMENTING_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public FermenterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(FlavoredBlockEntities.FERMENTER.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FermenterBlockEntity.this.progress;
                    case 1 -> FermenterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FermenterBlockEntity.this.progress = pValue;
                    case 1 -> FermenterBlockEntity.this.maxProgress = pValue;
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

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.flavored.fermenter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FermenterMenu(containerId, playerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {

        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("fermenter.progress", progress);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("fermenter.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                fermentItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void fermentItem() {
        Optional<RecipeHolder<FermentingRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().output();

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.extractItem(FERMENTING_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<FermentingRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResultItem(null);
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output.getItem());
    }

    private Optional<RecipeHolder<FermentingRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(FlavoredRecipes.FERMENTER_TYPE.get(), new FermentingRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT), itemHandler.getStackInSlot(FERMENTING_SLOT)), level);
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

    private void increaseCraftingProgress() {
        progress++;
    }

    public  ItemStackHandler getInventory() {
        return  this.itemHandler;
    }
}
