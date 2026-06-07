package com.sidden.flavored.menu;

import com.sidden.flavored.block.entity.MixingBowlBlockEntity;
import com.sidden.flavored.recipe.MixingRecipe;
import com.sidden.flavored.recipe.input.MixingRecipeInput;
import com.sidden.flavored.client.screen.MixingRecipeBookComponent;
import com.sidden.flavored.registry.FlavoredBlocks;
import com.sidden.flavored.registry.FlavoredMenus;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MixingBowlMenu extends RecipeBookMenu<MixingRecipeInput, MixingRecipe> {
    public final MixingBowlBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final ItemStackHandler inventory;
    private MixingRecipeBookComponent recipeBook;
    private final ResultContainer resultContainer = new ResultContainer();

    public MixingBowlMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {

        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(8));
        this.recipeBook = new MixingRecipeBookComponent();
    }

    public MixingBowlMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(FlavoredMenus.MIXING_BOWL.get(), pContainerId);
        checkContainerSize(inv, 9);
        blockEntity = ((MixingBowlBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;
        this.inventory = blockEntity.getInventory();

        int x = 0;
        int y = 0;
        int step = 18;
        Vec2 size = new Vec2(3, 2);

        for (int i = 0; i < 6; i++) {

            this.addSlot(new SlotItemHandler(this.inventory, i, 44 + step * x, 18 + step * y));

            x++;
            if (x >= size.x) {
                x = 0;
                y++;
            }
        }

        this.addSlot(new SlotItemHandler(this.inventory, 6, 135, 25));
        this.addSlot(new SlotItemHandler(this.inventory, 7, 62, 60));
        this.addSlot(new NonInteractiveResultSlot(this.resultContainer, 0, 106, 60));

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return this.data.get(2) == 1;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_SLOT_COUNT = 9;

    private static final int VANILLA_FIRST_SLOT_INDEX = TE_INVENTORY_SLOT_COUNT; // = 8
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT; // = 36

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // Clicked a TE slot > move to player inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // Clicked a player inventory/hotbar slot > move to TE inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        blockEntity.resetProgress();
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, FlavoredBlocks.MIXING_BOWL.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            itemHelper.accountStack(stack);
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
        NonNullList<ItemStack> ingredients = NonNullList.create();
        for (int i = 0; i < 9; i++) {
            ingredients.add(i, this.getSlot(i).getItem());
        }

        return recipe.value().matches(
                new MixingRecipeInput(ingredients, this.getSlot(6).getItem(), this.getSlot(7).getItem()),
                this.level
        );
    }

    public boolean recipeMatches(MixingRecipe recipe) {
        NonNullList<ItemStack> ingredients = NonNullList.create();
        for (int i = 0; i < 9; i++) {
            ingredients.add(i, this.getSlot(i).getItem());
        }

        return recipe.matches(
                new MixingRecipeInput(ingredients, this.getSlot(6).getItem(), this.getSlot(7).getItem()),
                this.level
        );
    }

    @Override
    public int getResultSlotIndex() {
        return 9;
    }

    @Override
    public int getGridWidth() {
        return 4;
    }

    @Override
    public int getGridHeight() {
        return 2;
    }

    @Override
    public int getSize() {
        return 9;
    }


    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.valueOf("FLAVORED_MIXING_BOWL");
    }

    @Override
    public boolean shouldMoveToInventory(int slotIndex) {
        return true;
    }

}