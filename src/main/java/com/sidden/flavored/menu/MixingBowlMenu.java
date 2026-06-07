package com.sidden.flavored.menu;

import com.sidden.flavored.block.entity.MixingBowlBlockEntity;
import com.sidden.flavored.recipe.MixingRecipe;
import com.sidden.flavored.recipe.input.MixingRecipeInput;
import com.sidden.flavored.registry.FlavoredMenus;
import net.minecraft.core.NonNullList;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;

import java.util.List;

public class MixingBowlMenu extends RecipeBookMenu<MixingRecipeInput, MixingRecipe> {
    public static final int INGREDIENT_SLOT_START = 0;
    public static final int INGREDIENT_SLOT_END = 6;
    public static final int VESSEL_SLOT = 6;
    public static final int LIQUID_SLOT = 7;
    public static final int SLOT_COUNT = 8;
    public static final int INV_SLOT_START = 8;
    public static final int INV_SLOT_END = 35;
    public static final int USE_ROW_SLOT_START = 35;
    public static final int USE_ROW_SLOT_END = 44;
    public static final int MIX_PROGRESS_ARROW_SIZE = 26;

    private final Container container;
    private final ContainerData data;
    private final Level level;

    public MixingBowlMenu(int containerId, Inventory playerInventory) {
        this(
                containerId,
                playerInventory,
                new SimpleContainer(8),
                new SimpleContainerData(2)
        );
    }

    public MixingBowlMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(FlavoredMenus.MIXING_BOWL.get(), containerId);

        checkContainerSize(playerInventory, 9);
        checkContainerDataCount(data, 2);

        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();

        // Adds the 3x2 grid
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                this.addSlot(new Slot(
                        this.container,
                        x + y * 3,
                        44 + x * 18,
                        18 + y * 18
                ));
            }
        }

        this.addSlot(new Slot(this.container, 6, 135, 25));
        this.addSlot(new Slot(this.container, 7, 62, 60));

        // Adds the player inventory
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 9; y++) {
                this.addSlot(new Slot(playerInventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
            }
        }

        // Adds the player hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(data);
    }

    public int getMixProgress() {
        int progress = this.data.get(0);

        return progress != 0 ? progress * MIX_PROGRESS_ARROW_SIZE / MixingBowlBlockEntity.MAX_MIX_PROGRESS : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            stack = stackInSlot.copy();

            if (index >= INV_SLOT_START) {
                if (this.isVessel(stackInSlot) && !this.moveItemStackTo(stackInSlot, VESSEL_SLOT, VESSEL_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
                if (this.isLiquid(stackInSlot) && !this.moveItemStackTo(stackInSlot, LIQUID_SLOT, LIQUID_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
                if (!this.moveItemStackTo(stackInSlot, INGREDIENT_SLOT_START, INGREDIENT_SLOT_END, false)) {
                    if (index < INV_SLOT_END) {
                        if (!this.moveItemStackTo(stackInSlot, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.moveItemStackTo(stackInSlot, INV_SLOT_START, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(stackInSlot, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return stack;
    }

    public boolean isVessel(ItemStack stack) {
        RecipeManager recipeManager = this.level.getRecipeManager();
        List<RecipeHolder<MixingRecipe>> holders = recipeManager.getAllRecipesFor(FlavoredRecipeTypes.MIXING_BOWL_TYPE.get());
        if (holders.isEmpty()) return false;

        return holders.stream().anyMatch(holder -> holder
                .value()
                .vesselInput()
                .test(stack)
        );
    }

    public boolean isLiquid(ItemStack stack) {
        RecipeManager recipeManager = this.level.getRecipeManager();
        List<RecipeHolder<MixingRecipe>> holders = recipeManager.getAllRecipesFor(FlavoredRecipeTypes.MIXING_BOWL_TYPE.get());
        if (holders.isEmpty()) return false;

        return holders.stream().anyMatch(holder -> holder
                .value()
                .liquidInput()
                .test(stack)
        );
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        if (this.container instanceof StackedContentsCompatible compatibleContainer) {
            compatibleContainer.fillStackedContents(itemHelper);
        }
    }

    @Override
    public void clearCraftingContent() {
        for (int i = 0; i < 9; i++) {
            this.getSlot(i).set(ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(RecipeHolder<MixingRecipe> recipe) {
        return recipe.value().matches(this.createInput(), this.level);
    }

    public MixingRecipeInput createInput() {
        int ingredientCount = MixingBowlBlockEntity.INGREDIENT_SLOTS.length;
        NonNullList<ItemStack> ingredients = NonNullList.withSize(ingredientCount, ItemStack.EMPTY);

        for (int i = 0; i < ingredientCount; i++) {
            ingredients.set(i, this.container.getItem(i));
        }

        return new MixingRecipeInput(
                ingredients,
                this.container.getItem(MixingBowlBlockEntity.VESSEL_SLOT),
                this.container.getItem(MixingBowlBlockEntity.LIQUID_SLOT)
        );
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.valueOf("FLAVORED_MIXING_BOWL");
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return true;
    }

    @Override
    public int getResultSlotIndex() {
        return 9;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 2;
    }

    @Override
    public int getSize() {
        return 8;
    }
}