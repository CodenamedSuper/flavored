package com.sidden.flavored.menu;

import com.sidden.flavored.block.entity.OvenBlockEntity;
import com.sidden.flavored.recipe.BakingRecipe;
import com.sidden.flavored.slot.OvenFuelSlot;
import com.sidden.flavored.registry.FlavoredMenus;
import com.sidden.flavored.registry.FlavoredRecipeTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class OvenMenu extends RecipeBookMenu<CraftingInput, BakingRecipe>  {
    public static final int INPUT_SLOT_START = 0;
    public static final int INPUT_SLOT_END = 9;
    public static final int FUEL_SLOT = 9;
    public static final int RESULT_SLOT = 10;
    public static final int SLOT_COUNT = 11;
    public static final int DATA_COUNT = 4;
    private static final int INV_SLOT_START = 11;
    private static final int INV_SLOT_END = 38;
    private static final int USE_ROW_SLOT_START = 38;
    private static final int USE_ROW_SLOT_END = 47;
    private final Container container;
    private final ContainerData data;
    protected final Level level;

    public OvenMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(SLOT_COUNT), new SimpleContainerData(DATA_COUNT));
    }

    public OvenMenu(int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(FlavoredMenus.OVEN.get(), containerId);
        checkContainerSize(container, SLOT_COUNT);
        checkContainerDataCount(data, DATA_COUNT);
        this.container = container;
        this.data = data;
        this.level = playerInventory.player.level();
        for (int slot = INPUT_SLOT_START; slot < INPUT_SLOT_END; slot++) {
            int x = slot % 3;
            int y = slot / 3;
            this.addSlot(new Slot(container, slot, 30 + 18 * x, 17 + 18 * y));
        }
        this.addSlot(new OvenFuelSlot(this, container, FUEL_SLOT, 126, 57));
        this.addSlot(new FurnaceResultSlot(playerInventory.player, container, RESULT_SLOT, 126, 17));

        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();
            if (index == RESULT_SLOT) {
                if (!this.moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(slotStack, itemstack);
            } else if (index >= INV_SLOT_START) {
                if (!this.moveItemStackTo(slotStack, INPUT_SLOT_START, INPUT_SLOT_END, false)) {
                    if (!this.isFuel(slotStack) || !this.moveItemStackTo(slotStack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                        if (index < INV_SLOT_END) {
                            if (!this.moveItemStackTo(slotStack, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (!this.moveItemStackTo(slotStack, INV_SLOT_START, INV_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.moveItemStackTo(slotStack, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return itemstack;
    }

    public boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(FlavoredRecipeTypes.OVEN_TYPE.get()) > 0;
    }

    public float getBurnProgress() {
        int progress = this.data.get(OvenBlockEntity.DATA_BAKING_PROGRESS);
        int totalTime = this.data.get(OvenBlockEntity.DATA_BAKING_TOTAL_TIME);
        return totalTime != 0 && progress != 0 ? Mth.clamp((float)progress / totalTime, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {
        int litDuration = this.data.get(OvenBlockEntity.DATA_LIT_DURATION);
        if (litDuration == 0) {
            litDuration = 200;
        }

        return Mth.clamp((float)this.data.get(OvenBlockEntity.DATA_LIT_TIME) / litDuration, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.data.get(OvenBlockEntity.DATA_LIT_TIME) > 0;
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        for (int i = 0; i < INPUT_SLOT_END; i++) {
            itemHelper.accountStack(this.container.getItem(i));
        }
    }

    @Override
    public boolean recipeMatches(RecipeHolder<BakingRecipe> recipe) {
        return recipe.value().matches(
                CraftingInput.of(3, 3,
                        List.of(
                                container.getItem(0), container.getItem(1), container.getItem(2),
                                container.getItem(3), container.getItem(4), container.getItem(5),
                                container.getItem(6), container.getItem(7), container.getItem(8)
                        )
                ), this.level
        );
    }

    @Override
    public void clearCraftingContent() {
        for (int i = 0; i < 8; i++) {
            this.getSlot(i).set(ItemStack.EMPTY);
        }
    }

    @Override
    public int getResultSlotIndex() {
        return 10;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public int getSize() {
        return 11;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.valueOf("FLAVORED_OVEN");
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return true;
    }
}
