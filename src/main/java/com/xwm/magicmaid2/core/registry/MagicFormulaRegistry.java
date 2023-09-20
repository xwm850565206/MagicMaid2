package com.xwm.magicmaid2.core.registry;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid.object.tileentity.Formula;
import com.xwm.magicmaid.object.tileentity.FormulaUpLevelComponent;
import com.xwm.magicmaid.object.tileentity.Result;
import com.xwm.magicmaid.object.tileentity.ResultNBTTagCompound;
import com.xwm.magicmaid.util.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

import static com.xwm.magicmaid.registry.MagicFormulaRegistry.registerFormula;
import static com.xwm.magicmaid2.core.init.ItemInit.*;

public class MagicFormulaRegistry
{
    public static void registerAllFormula() {

        registerFormula(Items.IRON_INGOT.getRegistryName(), Formula.create(Items.IRON_INGOT, 1, ItemInit.ITEM_EVIL),
                Result.create(Lists.newArrayList(new ItemStack(com.xwm.magicmaid2.core.init.ItemInit.EVIL_INGOT))), 200);

        registerFormula(ItemInit.ITEM_GHOST_OBSESSION_SWORD.getRegistryName(), Formula.create(ItemInit.ITEM_GHOST_OBSESSION_SWORD, 1, ItemInit.ITEM_EVIL, ItemInit.ITEM_EVIL, ItemInit.ITEM_EVIL, ItemInit.ITEM_EVIL),
                Result.create(Lists.newArrayList(new ItemStack(com.xwm.magicmaid2.core.init.ItemInit.EVIL_GHOST_OBSESSION_SWORD))), 200);

        String levelKey = Reference.MODID + "_level";
        registerFormula(ItemInit.ITEM_REPANTENCE.getRegistryName(), FormulaUpLevelComponent.create(new ItemStack(UNREAL_RING), levelKey, new ItemStack(ItemInit.ITEM_HOLY_STONE), new ItemStack(ItemInit.ITEM_HOLY_STONE)),
                ResultNBTTagCompound.create(new ArrayList<>(), Result.MetadataTypes.INCREASE, 1, levelKey), 50);

        registerFormula(ItemInit.ITEM_CONVICTION.getRegistryName(), FormulaUpLevelComponent.create(new ItemStack(UNREAL_BALL), levelKey, new ItemStack(ItemInit.ITEM_HOLY_STONE), new ItemStack(ItemInit.ITEM_HOLY_STONE)),
                ResultNBTTagCompound.create(new ArrayList<>(), Result.MetadataTypes.INCREASE, 1, levelKey), 50);

        registerFormula(ItemInit.ITEM_PANDORA.getRegistryName(), FormulaUpLevelComponent.create(new ItemStack(STAR_BOW), levelKey, new ItemStack(ItemInit.ITEM_HOLY_STONE), new ItemStack(ItemInit.ITEM_HOLY_STONE)),
                ResultNBTTagCompound.create(new ArrayList<>(), Result.MetadataTypes.INCREASE, 1, levelKey), 50);

        registerFormula(ItemInit.ITEM_WHISPER.getRegistryName(), FormulaUpLevelComponent.create(new ItemStack(DARK_CLAW), levelKey, new ItemStack(ItemInit.ITEM_HOLY_STONE), new ItemStack(ItemInit.ITEM_HOLY_STONE)),
                ResultNBTTagCompound.create(new ArrayList<>(), Result.MetadataTypes.INCREASE, 1, levelKey), 50);

        registerFormula(ItemInit.ITEM_DEMON_KILLER_SWORD.getRegistryName(), FormulaUpLevelComponent.create(new ItemStack(DARK_SOUL), levelKey, new ItemStack(ItemInit.ITEM_HOLY_STONE), new ItemStack(ItemInit.ITEM_HOLY_STONE)),
                ResultNBTTagCompound.create(new ArrayList<>(), Result.MetadataTypes.INCREASE, 1, levelKey), 50);
    }
}
