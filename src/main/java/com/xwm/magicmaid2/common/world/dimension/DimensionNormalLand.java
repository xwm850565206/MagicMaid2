package com.xwm.magicmaid2.common.world.dimension;


import com.xwm.magicmaid.manager.IMagicBossManager;
import com.xwm.magicmaid.manager.IMagicBossManagerImpl;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.particle.SPacketSixParamParticle;
import com.xwm.magicmaid.particle.EnumCustomParticles;
import com.xwm.magicmaid.store.WorldDifficultyData;
import com.xwm.magicmaid.util.handlers.PunishOperationHandler;
import com.xwm.magicmaid2.core.init.BiomeInit;
import com.xwm.magicmaid2.core.init.DimensionInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DimensionNormalLand extends WorldProvider
{
    private IMagicBossManager fightManager;
    private Random random = new Random();

    public void init()
    {
//        this.biomeProvider = new BiomeProvider(new WorldInfo(world.getWorldInfo()));;
        this.biomeProvider = new NormalLandBiomeProvider(world);
        this.hasSkyLight = true;
        NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(this.world.provider.getDimension());
        this.fightManager = this.world instanceof WorldServer ? new ITemporaryMagicBossManagerImpl((WorldServer)this.world, nbttagcompound.getCompoundTag("MaidFight")) : null;
    }

    public BlockPos getSpawnCoordinate()
    {
        return new BlockPos(0, 60, 0);
    }


    public int getAverageGroundLevel()
    {
        return 50;
    }


    public boolean canCoordinateBeSpawn(int x, int z)
    {
        return this.world.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
    }


    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return super.calculateCelestialAngle(worldTime, partialTicks);
    }

    /**
     * Returns array with sunrise/sunset colors
     */
    @Nullable
    @SideOnly(Side.CLIENT)
    public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
    {
        float[] colorsSunriseSunset =  super.calcSunriseSunsetColors(celestialAngle, partialTicks);
        return colorsSunriseSunset;
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getSkyColor(Entity cameraEntity, float partialTicks)
    {
        return super.getSkyColor(cameraEntity, partialTicks);
    }

    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
    {
        return new Vec3d(0, 0 ,0);
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionInit.RUIN_FOREST;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorNormalLand(world, getSeed(), true, world.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public boolean canRespawnHere()
    {
        return true;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 255.0F;
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return 1;
    }

    public boolean shouldClientCheckLighting(){
        return true;
    }

    @SideOnly(Side.CLIENT)
    public float getSunBrightness(float par1)
    {
        return world.getSunBrightnessBody(par1);
    }


    /**
     * Called when a Player is added to the provider's world.
     */
    public void onPlayerAdded(EntityPlayerMP player)
    {
        if (fightManager != null) {
            fightManager.addPlayer(player);
        }
    }

    @Override
    public void onPlayerRemoved(EntityPlayerMP player)
    {
        if (this.fightManager != null)
        {
            this.fightManager.removePlayer(player);
        }
    }

    public void onWorldUpdateEntities()
    {
        if (this.fightManager != null && !world.isRemote)
        {
            this.fightManager.tick();
        }

        super.onWorldUpdateEntities();
    }

    public void onWorldSave()
    {
        if (fightManager != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            nbttagcompound.setTag("MaidFight", this.fightManager.getCompound());

            this.world.getWorldInfo().setDimensionData(this.world.provider.getDimension(), nbttagcompound);
        }
        super.onWorldSave();
    }

    public IMagicBossManager getFightManager() {
        return this.fightManager;
    }

    public void setFightManager(IMagicBossManager fightManager) {
        this.fightManager = fightManager;
    }
}
