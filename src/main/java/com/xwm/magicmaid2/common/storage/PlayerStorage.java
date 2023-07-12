package com.xwm.magicmaid2.common.storage;

import com.xwm.magicmaid.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStorage extends WorldSavedData
{

    private static PlayerStorage clientStorage = null;
    private static String DATA_NAME = Reference.MODID + "_player_save_point";

    private Map<UUID, NBTTagCompound> playerData = new HashMap<>();
    private Map<UUID, Integer> playerDimension = new HashMap<>();

    public PlayerStorage() {
        super(DATA_NAME);
    }

    public PlayerStorage(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey("players"))
            return;
        NBTTagList tagList = nbt.getTagList("players", 10);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            UUID uuid = compound.getUniqueId("uuid");
            NBTTagCompound data = compound.getCompoundTag("data");
            int dimension = compound.getInteger("dimension");
            playerData.put(uuid, data);
            playerDimension.put(uuid, dimension);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        playerData.forEach(((uuid, nbtTagCompound) -> {
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setUniqueId("uuid", uuid);
            compound1.setTag("data", nbtTagCompound);
            compound1.setInteger("dimension", playerDimension.get(uuid));
            tagList.appendTag(compound1);
        }));
        compound.setTag("players", tagList);
        return compound;
    }

    public NBTTagCompound getPlayerData(UUID uuid)
    {
        return playerData.get(uuid);
    }

    public void setPlayerData(EntityPlayer player)
    {
        this.playerData.put(player.getUniqueID(),player.writeToNBT(new NBTTagCompound()));
        markDirty();
    }

    public int getPlayerDimension(UUID uuid)
    {
        return playerDimension.get(uuid);
    }

    public void setPlayerDimension(EntityPlayer player)
    {
        this.playerDimension.put(player.getUniqueID(), player.dimension);
        markDirty();
    }

    public static PlayerStorage get(World world)
    {
        if (world == null || world.isRemote) {
            if (clientStorage == null)
                clientStorage = new PlayerStorage();
            return clientStorage;
        }
        else {
            MapStorage storage = world.getMapStorage();
            PlayerStorage record = (PlayerStorage) storage.getOrLoadData(PlayerStorage.class, DATA_NAME);

            if (record == null) {
                record = new PlayerStorage();
                storage.setData(DATA_NAME, record);
                world.setData(DATA_NAME, record);
            }

            return record;
        }
    }
}
