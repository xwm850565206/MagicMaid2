package com.xwm.magicmaid2.core.registry;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid.object.tileentity.Formula;
import com.xwm.magicmaid.object.tileentity.Result;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.xwm.magicmaid.registry.MagicFormulaRegistry.registerFormula;

public class MagicFormulaRegistry
{
    public static void registerAllFormula() {

        registerFormula(Items.IRON_INGOT.getRegistryName(), Formula.create(Items.IRON_INGOT, 1, ItemInit.ITEM_EVIL),
                Result.create(Lists.newArrayList(new ItemStack(com.xwm.magicmaid2.core.init.ItemInit.EVIL_INGOT))), 200);

        registerFormula(ItemInit.ITEM_GHOST_OBSESSION_SWORD.getRegistryName(), Formula.create(ItemInit.ITEM_GHOST_OBSESSION_SWORD, 1, ItemInit.ITEM_EVIL, ItemInit.ITEM_EVIL, ItemInit.ITEM_EVIL, ItemInit.ITEM_EVIL),
                Result.create(Lists.newArrayList(new ItemStack(com.xwm.magicmaid2.core.init.ItemInit.EVIL_GHOST_OBSESSION_SWORD))), 200);

    }
}
