package com.xwm.magicmaid2.common.tileentity;

import com.xwm.magicmaid2.common.block.BlockSymbol;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntitySymbol extends TileEntity
{
    private int meta;
    private int index;

    private boolean isLock;
    private Random random = new Random();

    public TileEntitySymbol()
    {
        this.meta = random.nextInt(4);
        this.index = random.nextInt(8);
        this.isLock = false;
        markDirty();
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
        markDirty();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        markDirty();
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean isLock) {
        this.isLock = isLock;
        markDirty();
    }

    public void change() {
        this.index++;
        markDirty();
    }

    public ItemStack getSymbol() {
        switch (this.meta) {
            case 0:
                return BlockSymbol.SYMBOL_0.get(this.index%2);
            case 1:
                return BlockSymbol.SYMBOL_1.get(this.index%4);
            case 2:
                return BlockSymbol.SYMBOL_2.get(this.index%5);
            case 3:
                return BlockSymbol.SYMBOL_3.get(this.index%8);
            default:
                return BlockSymbol.SYMBOL_0.get(this.index%2);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.meta = compound.getInteger("meta");
        this.index = compound.getInteger("index");
        this.isLock = compound.getBoolean("lock");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound compound1 = super.writeToNBT(compound);
        compound1.setInteger("meta", this.meta);
        compound1.setInteger("index", this.index);
        compound1.setBoolean("lock", this.isLock);
        return compound1;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        NBTTagCompound tag = pkt.getNbtCompound();
        this.readFromNBT(tag);
    }
}
