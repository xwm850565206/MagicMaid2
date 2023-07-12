package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid.object.block.BlockBase;
import com.xwm.magicmaid2.common.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static Block BLOCK_EVIL_GRASS = new BlockEvilGrass("evil_grass", Material.GRASS);
    public static Block BLOCK_EVIL_DIRT = new BlockBase("evil_dirt", Material.GROUND);

    public static Block BLOCK_EVIL_TALL_GRASS = new BlockEvilTallGrass("evil_tall_grass");

    public static Block BLOCK_FLOWER_EVIL = new BlockEvilTallGrass("flower_evil");

    public static Block BLOCK_FOREST_PORTAL = new BlockForestPortal("forest_portal");
    public static Block BLOCK_MEMORY_CLOCK = new BlockMemoryClock("memory_clock");

    public static Block BLOCK_CORPSE = new BlockCorpse("corpse");
    public static Block BLOCK_DEDICATION_DEAD = new BlockDedicationDead("dedication_dead");
    public static Block BLOCK_SYMBOL = new BlockSymbol("symbol");
    public static Block BLOCK_CANDLE_STICK = new BlockCandleStick("candle_stick");
    public static Block BLOCK_ROUTU_BLOCK = new BlockRoutu("routu_block");

    public static Block BLOCK_MAGIC_CIRCLE_LEVEL_1 = new BlockMagicCircleLevel1("magic_circle_level_1");


    public static final Block DEDICATION_EXP_BLOCK = new BlockFluidDedicationExp("dedication_exp", FluidInit.DEDICATION_EXP_FLUID, Material.WATER);
    public static final BlockFluidDedicationLife DEDICATION_LIFE_BLOCK = new BlockFluidDedicationLife("dedication_life", FluidInit.DEDICATION_LIFE_FLUID, Material.WATER);
}
