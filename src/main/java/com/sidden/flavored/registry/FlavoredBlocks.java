package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import com.sidden.flavored.block.*;
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

    public static final DeferredBlock<Block> ROCK_SALT = registerBlock("rock_salt",
            () -> new RockSaltBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.STONE)));

    public static final DeferredBlock<Block> BUDDING_ROCK_SALT = registerBlock("budding_rock_salt",
            () -> new BuddingRockSaltBlock(BlockBehaviour.Properties.ofFullCopy(ROCK_SALT.get()).randomTicks().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> SALT_CLUSTER = registerBlock("salt_cluster",
            () -> new SaltClusterBlock(7.0F, 3.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER).sound(SoundType.GLASS)));

    public static final DeferredBlock<Block> SMALL_SALT_BUD = registerBlock("small_salt_bud",
            () -> new SaltClusterBlock(5.0F, 3.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.SMALL_AMETHYST_BUD).sound(SoundType.GLASS)));

    public static final DeferredBlock<Block> MEDIUM_SALT_BUD = registerBlock("medium_salt_bud",
            () -> new SaltClusterBlock(4.0F, 3.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.MEDIUM_AMETHYST_BUD).sound(SoundType.GLASS)));

    public static final DeferredBlock<Block> LARGE_SALT_BUD = registerBlock("large_salt_bud",
            () -> new SaltClusterBlock(3.0F, 4.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER).sound(SoundType.GLASS)));

    public static final DeferredBlock<Block> SALT_BLOCK = registerBlock("salt_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ROCK_SALT.get())));

    public static final DeferredBlock<Block> CINNAMON_STALK = registerBlock("cinnamon_stalk",
            () -> new CinnamonStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG)));

    public static final DeferredBlock<Block> STRIPPED_CINNAMON_STALK = registerBlock("stripped_cinnamon_stalk",
            () -> new StrippedCinnamonStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_JUNGLE_LOG)));

    public static final DeferredBlock<Block> CINNAMON_SPROUT = registerBlock("cinnamon_sprout",
            () -> new CinnamonSproutBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    public static final DeferredBlock<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD)));

    public static final DeferredBlock<Block> TOMATO_BUSH = registerBlock("tomato_bush",
            () -> new TomatoBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> CUCUMBER_VINE = registerBlock("cucumber_vine",
            () -> new CucumberVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)));

    public static final DeferredBlock<Block> SOFT_CHEESE = registerBlock("soft_cheese",
            ()-> new SoftCheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).randomTicks().strength(0.3f)));

    public static final DeferredBlock<Block> AGED_CHEESE = registerBlock("aged_cheese",
            ()-> new AgedCheeseBlock(BlockBehaviour.Properties.ofFullCopy(SOFT_CHEESE.get()).randomTicks()));


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
