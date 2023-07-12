package com.xwm.magicmaid2.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCorpse extends TileEntity
{
    private boolean active;
    private boolean gift;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.active = compound.getBoolean("active");
        this.gift = compound.getBoolean("gift");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("active", active);
        compound.setBoolean("gift", gift);
        return super.writeToNBT(compound);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        markDirty();
    }

    public boolean isGift() {
        return gift;
    }

    public void setGift(boolean gift) {
        this.gift = gift;
        markDirty();
    }
}
