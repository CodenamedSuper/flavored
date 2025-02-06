package com.codenamed.flavored.registry;

import com.codenamed.flavored.Flavored;
import com.codenamed.flavored.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
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

    // Chocolate Blocks

    public static final DeferredBlock<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD)));


    // Salt Blocks

    public static final DeferredBlock<Block> ROCK_SALT = registerBlock("rock_salt",
            () -> new RockSaltBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK)));

    public static final DeferredBlock<Block> BUDDING_ROCK_SALT = registerBlock("budding_rock_salt",
            () -> new BuddingRockSaltBlock(BlockBehaviour.Properties.ofFullCopy(ROCK_SALT.get())));

    public static final DeferredBlock<Block> SALT_CLUSTER = registerBlock("salt_cluster",
            () -> new SaltClusterBlock(7.0F, 3.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER)));

    public static final DeferredBlock<Block> SMALL_SALT_BUD = registerBlock("small_salt_bud",
            () -> new SaltClusterBlock(5.0F, 3.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.SMALL_AMETHYST_BUD)));

    public static final DeferredBlock<Block> MEDIUM_SALT_BUD = registerBlock("medium_salt_bud",
            () -> new SaltClusterBlock(4.0F, 3.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD)));

    public static final DeferredBlock<Block> LARGE_SALT_BUD = registerBlock("large_salt_bud",
            () -> new SaltClusterBlock(3.0F, 4.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER)));

    public static final DeferredBlock<Block> SALT_BLOCK = registerBlock("salt_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ROCK_SALT.get())));

    // Workstations

    public static final DeferredBlock<Block> FERMENTER = registerBlock("fermenter",
            () -> new FermenterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)));

    // Functional Blocks

    public static final DeferredBlock<Block> SALT_LAMP = registerBlock("salt_lamp",
            () -> new SaltLampBlock(BlockBehaviour.Properties.ofFullCopy(ROCK_SALT.get()).lightLevel(litBlockEmission(12)).noOcclusion().forceSolidOn()));

    // Crops

    public static final DeferredBlock<Block> TOMATO_BUSH = registerBlock("tomato_bush",
            () -> new TomatoBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> PEPPER_BUSH = registerBlock("pepper_bush",
            () -> new PepperBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> CUCUMBER_BUSH = registerBlock("cucumber_bush",
            () -> new CucumberBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> GARLICS = registerBlock("garlics",
            () -> new GarlicBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));

    // Wild Crops

    public static final DeferredBlock<Block> WILD_PEPPER = registerBlock("wild_pepper",
            () -> new WildPepperBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH)));

    public static final DeferredBlock<Block> WILD_CUCUMBER = registerBlock("wild_cucumber",
            () -> new WildCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    public static final DeferredBlock<Block> WILD_GARLIC = registerBlock("wild_garlic",
            () -> new WildCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));


    // Placeable Items

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
