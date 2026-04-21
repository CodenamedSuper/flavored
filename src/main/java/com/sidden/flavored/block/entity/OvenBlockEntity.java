package com.sidden.flavored.block.entity;

import com.google.common.collect.Lists;
import com.sidden.flavored.client.menu.OvenMenu;
import com.sidden.flavored.client.recipe.BakingRecipe;
import com.sidden.flavored.registry.FlavoredBlockEntities;
import com.sidden.flavored.registry.FlavoredRecipes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class OvenBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    protected static final int SLOT_INPUT_START = 0;
    protected static final int SLOT_INPUT_END = 9;
    protected static final int SLOT_FUEL = 9;
    protected static final int SLOT_RESULT = 10;
    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] SLOTS_FOR_DOWN = new int[]{SLOT_RESULT, SLOT_FUEL};
    private static final int[] SLOTS_FOR_SIDES = new int[]{SLOT_FUEL};
    public static final int DATA_LIT_TIME = 0;
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_BAKING_PROGRESS = 2;
    public static final int DATA_BAKING_TOTAL_TIME = 3;
    public static final int NUM_DATA_VALUES = 4;
    public static final int BURN_TIME_STANDARD = 200;
    public static final int BURN_COOL_SPEED = 2;
    protected NonNullList<ItemStack> items;
    int litTime;
    int litDuration;
    int bakingProgress;
    int bakingTotalTime;
    protected final ContainerData dataAccess;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed;
    private final RecipeManager.CachedCheck<CraftingInput, BakingRecipe> quickCheck;

    public OvenBlockEntity(BlockPos pos, BlockState blockState) {
        super(FlavoredBlockEntities.OVEN.get(), pos, blockState);
        this.items = NonNullList.withSize(11, ItemStack.EMPTY);
        this.dataAccess = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case DATA_LIT_TIME -> {
                        if (OvenBlockEntity.this.litDuration > 32767) {
                            yield Mth.floor((double) OvenBlockEntity.this.litTime / (double) OvenBlockEntity.this.litDuration * 32767.0);
                        }
                        yield OvenBlockEntity.this.litTime;
                    }
                    case DATA_LIT_DURATION -> Math.min(OvenBlockEntity.this.litDuration, 32767);
                    case DATA_BAKING_PROGRESS -> OvenBlockEntity.this.bakingProgress;
                    case DATA_BAKING_TOTAL_TIME -> OvenBlockEntity.this.bakingTotalTime;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case DATA_LIT_TIME -> OvenBlockEntity.this.litTime = value;
                    case DATA_LIT_DURATION -> OvenBlockEntity.this.litDuration = value;
                    case DATA_BAKING_PROGRESS -> OvenBlockEntity.this.bakingProgress = value;
                    case DATA_BAKING_TOTAL_TIME -> OvenBlockEntity.this.bakingTotalTime = value;
                }
            }

            public int getCount() {
                return NUM_DATA_VALUES;
            }
        };
        this.recipesUsed = new Object2IntOpenHashMap<>();
        this.quickCheck = RecipeManager.createCheck(FlavoredRecipes.OVEN_TYPE.get());
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.litTime = tag.getInt("BurnTime");
        this.bakingProgress = tag.getInt("BakeTime");
        this.bakingTotalTime = tag.getInt("BakeTimeTotal");
        this.litDuration = this.getBurnDuration(this.items.get(SLOT_FUEL));
        CompoundTag recipesUsed = tag.getCompound("RecipesUsed");
        for (String key : recipesUsed.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.parse(key), recipesUsed.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("BurnTime", this.litTime);
        tag.putInt("BakeTime", this.bakingProgress);
        tag.putInt("BakeTimeTotal", this.bakingTotalTime);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        final CompoundTag recipesUsed = new CompoundTag();
        this.recipesUsed.forEach((id, count) -> recipesUsed.putInt(id.toString(), count));
        tag.put("RecipesUsed", recipesUsed);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.oven");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, OvenBlockEntity blockEntity) {
        boolean isLit = blockEntity.isLit();
        boolean changed = false;
        if (blockEntity.isLit()) {
            --blockEntity.litTime;
        }

        ItemStack fuelStack = blockEntity.items.get(SLOT_FUEL);
        CraftingInput input = blockEntity.createRecipeInput();
        boolean hasFuel = !fuelStack.isEmpty();
        if (!blockEntity.isLit() && (!hasFuel || input.isEmpty())) {
            if (blockEntity.bakingProgress > 0) {
                blockEntity.bakingTotalTime = Mth.clamp(blockEntity.bakingProgress - BURN_COOL_SPEED, 0, blockEntity. bakingTotalTime);
            }
        } else {
            RecipeHolder<?> recipeholder;
            if (!input.isEmpty()) {
                recipeholder = blockEntity.quickCheck.getRecipeFor(input, level).orElse(null);
            } else {
                recipeholder = null;
            }

            int maxStackSize = blockEntity.getMaxStackSize();
            if (!blockEntity.isLit() && canBurn(level.registryAccess(), recipeholder, blockEntity.items, maxStackSize, blockEntity)) {
                blockEntity.litTime = blockEntity.getBurnDuration(fuelStack);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    changed = true;
                    if (fuelStack.hasCraftingRemainingItem()) {
                        blockEntity.items.set(SLOT_FUEL, fuelStack.getCraftingRemainingItem());
                    } else if (hasFuel) {
                        fuelStack.shrink(1);
                        if (fuelStack.isEmpty()) {
                            blockEntity.items.set(SLOT_FUEL, fuelStack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && canBurn(level.registryAccess(), recipeholder, blockEntity.items, maxStackSize, blockEntity)) {
                ++blockEntity.bakingProgress;
                if (blockEntity.bakingProgress == blockEntity.bakingTotalTime) {
                    blockEntity.bakingProgress = 0;
                    blockEntity.bakingTotalTime = getTotalBakeTime(level, blockEntity);
                    if (burn(level.registryAccess(), recipeholder, blockEntity.items, maxStackSize, blockEntity)) {
                        blockEntity.setRecipeUsed(recipeholder);
                    }

                    changed = true;
                }
            } else {
                blockEntity.bakingProgress = 0;
            }
        }

        if (isLit != blockEntity.isLit()) {
            changed = true;
            state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, OvenBlockEntity oven) {
        CraftingInput input = oven.createRecipeInput();
        if (!input.isEmpty() && recipe != null) {
            ItemStack resultStack = ((BakingRecipe) recipe.value()).assemble(input, registryAccess);
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outputStack = inventory.get(SLOT_RESULT);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(outputStack, resultStack)) {
                    return false;
                } else {
                    return outputStack.getCount() + resultStack.getCount() <= maxStackSize && outputStack.getCount() + resultStack.getCount() <= outputStack.getMaxStackSize() || outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private static boolean burn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize, OvenBlockEntity oven) {
        if (recipe != null && canBurn(registryAccess, recipe, inventory, maxStackSize, oven)) {
            ItemStack resultStack = ((BakingRecipe)recipe.value()).assemble(oven.createRecipeInput(), registryAccess);
            ItemStack outputStack = inventory.get(SLOT_RESULT);
            if (outputStack.isEmpty()) {
                inventory.set(SLOT_RESULT, resultStack.copy());
            } else if (ItemStack.isSameItemSameComponents(outputStack, resultStack)) {
                outputStack.grow(resultStack.getCount());
            }

            for (int slot = SLOT_INPUT_START; slot < SLOT_INPUT_END; slot++) {
                oven.getItem(slot).shrink(1);
            }
            return true;
        } else {
            return false;
        }
    }

    protected int getBurnDuration(ItemStack fuel) {
        return fuel.isEmpty() ? 0 : fuel.getBurnTime(FlavoredRecipes.OVEN_TYPE.get());
    }

    private static int getTotalBakeTime(Level level, OvenBlockEntity blockEntity) {
        CraftingInput input = blockEntity.createRecipeInput();
        return blockEntity.quickCheck.getRecipeFor(input, level).map(recipe -> recipe.value().getBakingTime()).orElse(BURN_TIME_STANDARD);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return switch (side) {
            case DOWN -> SLOTS_FOR_DOWN;
            case UP -> SLOTS_FOR_UP;
            default -> SLOTS_FOR_SIDES;
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return !(direction == Direction.DOWN && index == SLOT_FUEL && !stack.is(Items.BUCKET));
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    private CraftingInput createRecipeInput() {
        return CraftingInput.of(3, 3, this.items.subList(SLOT_INPUT_START, SLOT_INPUT_END));
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        if (index == 0 && !flag) {
            this.bakingTotalTime = getTotalBakeTime(this.level, this);
            this.bakingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new OvenMenu(i, inventory, this, this.dataAccess);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return switch (index) {
            case SLOT_RESULT -> false;
            case SLOT_FUEL -> stack.getBurnTime(FlavoredRecipes.OVEN_TYPE.get()) > 0;
            default -> true;
        };
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.id();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> recipes = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(recipes);

        for (RecipeHolder<?> recipeHolder : recipes) {
            if (recipeHolder != null) {
                player.triggerRecipeCrafted(recipeHolder, this.items);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> resourceLocationEntry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(resourceLocationEntry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                createExperience(level, popVec, resourceLocationEntry.getIntValue(), ((BakingRecipe) recipe.value()).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(ServerLevel level, Vec3 popVec, int recipeIndex, float experience) {
        int i = Mth.floor((float)recipeIndex * experience);
        float f = Mth.frac((float)recipeIndex * experience);
        if (f != 0.0F && Math.random() < (double)f) {
            ++i;
        }

        ExperienceOrb.award(level, popVec, i);
    }

    @Override
    public void fillStackedContents(StackedContents helper) {
        for (ItemStack itemstack : this.items) {
            helper.accountStack(itemstack);
        }
    }
}
