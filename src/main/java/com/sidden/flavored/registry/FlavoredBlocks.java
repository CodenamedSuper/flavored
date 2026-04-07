package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Flavored.MOD_ID);


    private static Block stair(Block baseBlock) {
        return new StairBlock(baseBlock.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(baseBlock));
    }

    private static Block flowerPot(Block potted) {
        return new FlowerPotBlock(potted, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerPuddingBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerPuddingBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerPureBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        FlavoredItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block> void registerPuddingBlockItem(String name, DeferredBlock<T> block) {
        FlavoredItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(16)));
    }
    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
