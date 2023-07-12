package com.xwm.magicmaid2.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityForestPortal extends TileEntity {
    public TileEntityForestPortal() {
        super();
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRenderFace(EnumFacing facing)
    {
        if (facing == EnumFacing.UP)
            return true;
        else
            return false;
    }
}