package com.sidden.flavored;

import com.sidden.flavored.registry.FlavoredItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class EnumParameters {
    public static final EnumProxy<RecipeBookCategories> FLAVORED_KEG_SEARCH = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(Items.COMPASS))
    );
    public static final EnumProxy<RecipeBookCategories> FLAVORED_KEG = new EnumProxy<>(
            RecipeBookCategories.class, (Supplier<List<ItemStack>>) () -> List.of(new ItemStack(FlavoredItems.PESTO_PASTA.get()))
    );
}