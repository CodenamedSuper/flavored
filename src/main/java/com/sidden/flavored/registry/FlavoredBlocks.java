package com.sidden.flavored.registry;

import com.mojang.serialization.MapCodec;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Flavored.MOD_ID);

    // Workstations

    public static final DeferredBlock<Block> KEG = registerBlock("keg",
            () -> new KegBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)));

    public static final DeferredBlock<Block> MIXING_BOWL = registerBlock("mixing_bowl",
            () -> new MixingBowlBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)));

    public static final DeferredBlock<Block> OVEN = registerBlock("oven",
            () -> new OvenBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F).lightLevel(blockstate -> blockstate.getValue(BlockStateProperties.LIT) ? 13 : 0)));

    // Salt Blocks

    public static final DeferredBlock<Block> ROCK_SALT = registerBlock("rock_salt",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.STONE)));

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

    // Cinnamon Blocks

    public static final DeferredBlock<Block> CINNAMON_STALK = registerBlock("cinnamon_stalk",
            () -> new CinnamonStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.JUNGLE_LOG)));

    public static final DeferredBlock<Block> STRIPPED_CINNAMON_STALK = registerBlock("stripped_cinnamon_stalk",
            () -> new StrippedCinnamonStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_JUNGLE_LOG).randomTicks()));

    public static final DeferredBlock<Block> CINNAMON_SPROUT = registerBlock("cinnamon_sprout",
            () -> new CinnamonSproutBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));

    // Crop Plants

    public static final DeferredBlock<Block> TOMATO_BUSH = registerBlock("tomato_bush",
            () -> new TomatoBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> CUCUMBER_VINE = registerBlock("cucumber_vine",
            () -> new CucumberVineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)));

    public static final DeferredBlock<Block> CORN_BUSH = registerBlock("corn_bush",
            () -> new CornBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> PEPPER_BUSH = registerBlock("pepper_bush",
            () -> new PepperBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> SPINACH_BUSH = registerBlock("spinach_bush",
            () -> new SpinachBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<Block> GARLICS = registerBlock("garlics",
            () -> new CropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));

    // Item Blocks

    public static final DeferredBlock<Block> SOFT_CHEESE = registerBlock("soft_cheese",
            ()-> new SoftCheeseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE).randomTicks().strength(0.3f)));

    public static final DeferredBlock<Block> AGED_CHEESE = registerBlock("aged_cheese",
            ()-> new AgedCheeseBlock(BlockBehaviour.Properties.ofFullCopy(SOFT_CHEESE.get()).randomTicks()));

    public static final DeferredBlock<Block> PUDDING = registerFoodBlock("pudding",
            ()-> new PuddingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));

    public static final DeferredBlock<Block> PIZZA = registerFoodBlock("pizza",
            ()-> new PizzaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));

    // Other Blocks

    public static final DeferredBlock<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_MUD)));


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

    private static <T extends Block> DeferredBlock<T> registerFoodBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerFoodBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerPureBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        FlavoredItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block> void registerFoodBlockItem(String name, DeferredBlock<T> block) {
        FlavoredItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(1)));
    }
    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
