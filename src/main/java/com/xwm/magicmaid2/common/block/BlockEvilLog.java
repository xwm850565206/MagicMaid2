package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.Main;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockEvilLog extends BlockLog implements IRegistrable, IHasModel
{
    public BlockEvilLog(String name) {
        super();
        setRegistryName(name);
        setUnlocalizedName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));

        doRegister();
    }

    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta & 7)
        {
            case 1:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        switch (state.getValue(LOG_AXIS))
        {
            case X:
                i |= 1;
                break;
            case Z:
                i |= 2;
                break;
            case NONE:
                i |= 4;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
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
