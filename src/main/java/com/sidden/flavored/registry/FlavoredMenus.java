package com.sidden.flavored.registry;


import com.sidden.flavored.Flavored;
import com.sidden.flavored.client.menu.KegMenu;
import com.sidden.flavored.Flavored;
import com.sidden.flavored.client.menu.MixingBowlMenu;
import com.sidden.flavored.client.menu.OvenMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FlavoredMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Flavored.MOD_ID);

    public static final Supplier<MenuType<KegMenu>> KEG =
            registerMenuType("keg", KegMenu::new);

    public static final Supplier<MenuType<MixingBowlMenu>> MIXING_BOWL =
            registerMenuType("mixing_bowl", MixingBowlMenu::new);

    public static final Supplier<MenuType<OvenMenu>> OVEN =
            registerMenuType("oven", () -> new MenuType<>(OvenMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, Supplier<MenuType<T>> supplier) {
        return MENUS.register(name, supplier);
    }

    private static <T extends AbstractContainerMenu>Supplier<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
    public static void init(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}