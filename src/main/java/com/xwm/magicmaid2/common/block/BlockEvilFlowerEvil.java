package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.Main;
import com.xwm.magicmaid.creativetab.CreativeTabMaid;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEvilFlowerEvil extends BlockBush implements IRegistrable, IHasModel
{

    public BlockEvilFlowerEvil(String name) {
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabMaid.CREATIVE_TAB_MAID);

        doRegister();
    }

    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == BlockInit.BLOCK_EVIL_GRASS || state.getBlock() == BlockInit.BLOCK_EVIL_DIRT;
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }
}
