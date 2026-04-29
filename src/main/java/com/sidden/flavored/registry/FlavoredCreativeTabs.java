package com.sidden.flavored.registry;

import com.sidden.flavored.Flavored;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;


public class FlavoredCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Flavored.MOD_ID);

    public static final Supplier<CreativeModeTab> FLAVORED = CREATIVE_MODE_TAB.register("flavored",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FlavoredItems.RED_TOMATO.get()))
                    .title(Component.translatable("creativetab.flavored.flavored"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(FlavoredBlocks.CHOCOLATE_BLOCK);
                        output.accept(FlavoredBlocks.CHOCOLATE_TILES);
                        output.accept(FlavoredBlocks.CHOCOLATE_TILE_STAIRS);
                        output.accept(FlavoredBlocks.CHOCOLATE_TILE_SLAB);
                        output.accept(FlavoredBlocks.CINNAMON_STALK);
                        output.accept(FlavoredBlocks.STRIPPED_CINNAMON_STALK);
                        output.accept(FlavoredBlocks.CINNAMON_SPROUT);
                        output.accept(FlavoredBlocks.KEG);
                        output.accept(FlavoredBlocks.OVEN);
                        output.accept(FlavoredBlocks.MIXING_BOWL);
                        output.accept(FlavoredItems.KNIFE);
                        output.accept(FlavoredItems.WHISK);
                        output.accept(FlavoredItems.GROUND_BEEF);
                        output.accept(FlavoredItems.COOKED_GROUND_BEEF);
                        output.accept(FlavoredItems.CHICKEN_DRUMSTICK);
                        output.accept(FlavoredItems.COOKED_CHICKEN_DRUMSTICK);
                        output.accept(FlavoredItems.MUTTON_SHANK);
                        output.accept(FlavoredItems.COOKED_MUTTON_SHANK);
                        output.accept(FlavoredItems.PORK_JOWL);
                        output.accept(FlavoredItems.COOKED_PORK_JOWL);
                        output.accept(FlavoredItems.GREEN_TOMATO);
                        output.accept(FlavoredItems.YELLOW_TOMATO);
                        output.accept(FlavoredItems.RED_TOMATO);
                        output.accept(FlavoredItems.GARLIC);
                        output.accept(FlavoredItems.CORN);
                        output.accept(FlavoredItems.PEPPER);
                        output.accept(FlavoredItems.SPINACH);
                        output.accept(FlavoredItems.CINNAMON);
                        output.accept(FlavoredItems.FLOUR);
                        output.accept(FlavoredItems.BUTTER);
                        output.accept(FlavoredItems.DOUGH);
                        output.accept(FlavoredItems.PASTRY_DOUGH);
                        output.accept(FlavoredItems.COOKIE_DOUGH);
                        output.accept(FlavoredItems.PASTA);
                        output.accept(FlavoredItems.BATTER);
                        output.accept(FlavoredItems.CHOCOLATE);
                        output.accept(FlavoredItems.TOMATO_SEEDS);
                        output.accept(FlavoredItems.CORN_SEEDS);
                        output.accept(FlavoredItems.PEPPER_SEEDS);
                        output.accept(FlavoredItems.SPINACH_SEEDS);
                        output.accept(FlavoredItems.SOFT_CHEESE_SLICE);
                        output.accept(FlavoredItems.AGED_CHEESE_SLICE);
                        output.accept(FlavoredBlocks.SOFT_CHEESE);
                        output.accept(FlavoredBlocks.AGED_CHEESE);
                        output.accept(FlavoredItems.DRIED_PEPPER);
                        output.accept(FlavoredItems.CHOCOLATE_EGG);
                        output.accept(FlavoredItems.GRILLED_CORN);
                        output.accept(FlavoredItems.HAM_SANDWICH);
                        output.accept(FlavoredItems.CHEESE_SANDWICH);
                        output.accept(FlavoredItems.HAMBURGER);
                        output.accept(FlavoredItems.GARLIC_BREAD);
                        output.accept(FlavoredItems.BUTTER_PASTRY);
                        output.accept(FlavoredItems.CINNAMON_PASTRY);
                        output.accept(FlavoredItems.CHOCOLATE_PASTRY);
                        output.accept(FlavoredItems.HONEY_PASTRY);
                        output.accept(FlavoredItems.PORRIDGE);
                        output.accept(FlavoredItems.POLENTA);
                        output.accept(FlavoredItems.SALAD);
                        output.accept(FlavoredItems.TOMATO_PASTA);
                        output.accept(FlavoredItems.PESTO_PASTA);
                        output.accept(FlavoredItems.CREAM_PASTA);
                        output.accept(FlavoredItems.CARBONARA_PASTA);
                        output.accept(FlavoredItems.RAGU_PASTA);
                        output.accept(FlavoredItems.OSSOBUCO);
                        output.accept(FlavoredItems.SHAKSHOUKA);
                        output.accept(FlavoredItems.CEREAL);
                        output.accept(FlavoredBlocks.PIZZA);
                        output.accept(FlavoredItems.PIZZA_SLICE);
                        output.accept(FlavoredBlocks.PUDDING);
                        output.accept(FlavoredItems.SWEET_BERRY_JUICE);
                        output.accept(FlavoredItems.GLOW_BERRY_JUICE);
                        output.accept(FlavoredItems.APPLE_JUICE);
                        output.accept(FlavoredItems.WORT);
                        output.accept(FlavoredItems.SWEET_BERRY_WINE);
                        output.accept(FlavoredItems.GLOW_BERRY_WINE);
                        output.accept(FlavoredItems.CIDER);
                        output.accept(FlavoredItems.BEER);
                        output.accept(FlavoredItems.CHOCKEN_SPAWN_EGG);


                    }).build());

    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
