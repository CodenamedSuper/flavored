package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import com.codenamed.flavored.block.AgedCheeseBlock;
import com.codenamed.flavored.block.RawCheeseBlock;
import com.codenamed.flavored.block.TomatoBushBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;


public class FlavoredBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Flavored.MOD_ID);

    public static final DeferredBlock<Block> TOMATO_BUSH = registerBlock("tomato_bush",
            () -> new TomatoBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> GARLICS = registerBlock("garlics",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));

    public static final DeferredBlock<Block> RAW_CHEESE = registerBlock("raw_cheese",
            () -> new RawCheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).randomTicks()));

    public static final DeferredBlock<Block> CHEESE = registerBlock("cheese",
            () -> new AgedCheeseBlock(BlockBehaviour.Properties.ofFullCopy(RAW_CHEESE.get())));


    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (p_50763_) -> {
            return (Boolean)p_50763_.getValue(BlockStateProperties.LIT) ? lightValue : 0;
        };
    }

    private static Block stair(DeferredBlock<Block> baseBlock) {
        return new StairBlock(baseBlock.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(baseBlock.get()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        FlavoredItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}