package com.sidden.flavored.slot;

import com.sidden.flavored.menu.OvenMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OvenFuelSlot extends Slot {
    private final OvenMenu menu;

    public OvenFuelSlot(OvenMenu menu, Container furnaceContainer, int slot, int xPosition, int yPosition) {
        super(furnaceContainer, slot, xPosition, yPosition);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack stack) {
        return this.menu.isFuel(stack);
    }
}
