package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockEvilBush extends BlockDeadBush implements IRegistrable, IHasModel
{
    public BlockEvilBush(String name) {
        super();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        doRegister();
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getBlock() == BlockInit.BLOCK_EVIL_GRASS;
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
