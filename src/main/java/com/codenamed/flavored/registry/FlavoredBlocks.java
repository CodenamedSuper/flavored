package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import com.codenamed.flavored.block.FermenterBlock;
import com.codenamed.flavored.block.PepperBushBlock;
import com.codenamed.flavored.block.WildPepperBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Flavored.MOD_ID);

    public static final DeferredBlock<Block> FERMENTER = registerBlock("fermenter",
            () -> new FermenterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)));

    public static final DeferredBlock<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD)));

    public static final DeferredBlock<Block> PEPPER_BUSH = registerBlock("pepper_bush",
            () -> new PepperBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> WILD_PEPPER = registerBlock("wild_pepper",
            () -> new WildPepperBlock(BlockBehaviour.Properties.ofFullCopy(PEPPER_BUSH.get())));

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
