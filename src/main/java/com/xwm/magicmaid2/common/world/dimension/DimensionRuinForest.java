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
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DimensionRuinForest extends WorldProvider
{
    private IMagicBossManager fightManager;
    private Random random = new Random();

    public void init()
    {
        this.biomeProvider = new BiomeProviderSingle(BiomeInit.FOG_FOREST);
        this.hasSkyLight = true;
        NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(this.world.provider.getDimension());
        this.fightManager = this.world instanceof WorldServer ? new IMagicBossManagerImpl((WorldServer)this.world, nbttagcompound.getCompoundTag("MaidFight")) : null;
    }

    public BlockPos getSpawnCoordinate()
    {
        return new BlockPos(0, 55, 0);
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
        if (cameraEntity instanceof EntityLivingBase) {
            if (((EntityLivingBase) cameraEntity).getActivePotionEffect(PotionInit.POTION_EVIL) != null)
                return new Vec3d(0, 0, 0);
            else if (((EntityLivingBase) cameraEntity).getActivePotionEffect(PotionInit.POTION_CRASY) != null)
                return new Vec3d(212, 0, 0);
        }

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
        return super.getFogColor(p_76562_1_, p_76562_2_);
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionInit.RUIN_FOREST;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorRuinForest(world, true, this.world.getSeed(), getSpawnCoordinate());
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public float getCloudHeight()
    {
        return 8.0F;
    }

    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z)
    {
        return true;
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

        if (this.getWorldTime() % 1000 == 0)
        {
            List<EntityPlayer> playerList = this.world.playerEntities;
            for (EntityPlayer player : playerList)
            {
                if (player.getActivePotionEffect(PotionInit.POTION_EVIL) == null) { // 没有邪恶药水的人会周期获得凋零效果
                    player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 500, 2));
                }
            }
        }

        if (this.getWorldTime() % 2000 == 0 & !world.isRemote)
        {
            List<EntityPlayer> playerList = this.world.playerEntities;
            for (EntityPlayer player : playerList)
            {
                double d0 = player.posX + random.nextFloat() * 3;
                double d1 = player.posY + random.nextFloat() * 3;
                double d2 = player.posZ + random.nextFloat() * 3;
                for (int i = 0; i < 2; i++) {
                    EntityVex vex = new EntityVex(world);
                    vex.setPosition(d0, d1, d2);
                    vex.setAttackTarget(player);
                    vex.moveToBlockPosAndAngles(player.getPosition(), 0.0F, 0.0F);
                    vex.onInitialSpawn(world.getDifficultyForLocation(player.getPosition()), (IEntityLivingData)null);
                    if (fightManager != null)
                        vex.setOwner(fightManager.getBoss());
                    vex.setBoundOrigin(player.getPosition());
                    vex.setLimitedLife(20 * (30 +  random.nextInt(90)));
                    world.spawnEntity(vex);
                }
            }

            if (this.world.loadedEntityList.size() > 30)
            {
                for (Entity entity : world.loadedEntityList)
                {
                    if (entity instanceof EntityVillager)
                        entity.setDead();
                }
            }

        }

        if (random.nextFloat() < 0.1 && !world.playerEntities.isEmpty() && !world.isRemote)
        {
            EntityPlayer player = world.playerEntities.get(random.nextInt(world.playerEntities.size()));
            double d0 = player.posX - random.nextFloat() * 10;
            double d1 = player.posY - random.nextFloat() * 2;
            double d2 = player.posZ - random.nextFloat() * 10;
            double d3 = player.posX + random.nextFloat() * 10;
            double d4 = player.posY + random.nextFloat() * 2;
            double d5 = player.posZ + random.nextFloat() * 10;
            for (int i = 0; i < 3; i++) {
                SPacketSixParamParticle packet = new SPacketSixParamParticle(
                        d0 - random.nextFloat() * 2,
                        d1 - random.nextFloat(),
                        d2 - random.nextFloat() * 2,
                        d3 + random.nextFloat() * 2,
                        d4 + random.nextFloat(),
                        d5 + random.nextFloat() * 2, EnumCustomParticles.PANDORA
                );
                NetworkLoader.instance.sendToDimension(packet, getDimension());
            }
        }

        int difficulty = WorldDifficultyData.get(world).getWorldDifficulty();
        if (this.getWorldTime() % 20 == 0 && difficulty >= 3)
        {
            for (EntityPlayer player : world.playerEntities)
            {
                if (player instanceof EntityPlayerMP)
                {
                    List<ItemStack> armors = player.inventory.armorInventory;
                    for (ItemStack stack : armors) {
                        try {
                            String domain = stack.getItem().getRegistryName().getResourceDomain();
                            if (IMagicBossManagerImpl.whiteDomain.contains(domain))
                                continue;
                        } catch (NullPointerException e) {
                            continue;
                        }

                        PunishOperationHandler.punishPlayer((EntityPlayerMP) player, 16, "检测到玩家携带其他模组防具，尝试掉落防具");
                        break;
                    }
                }
            }
        }

        if (this.getWorldTime() % 20 == 0 && difficulty >= 4)
        {
            for (EntityPlayer player : world.playerEntities)
            {
                if (player instanceof EntityPlayerMP)
                {
                    for (int i = 0; i < player.inventory.getSizeInventory(); i++)
                    {
                        ItemStack stack = player.inventory.getStackInSlot(i);
                        try {
                            String domain = stack.getItem().getRegistryName().getResourceDomain();
                            if (IMagicBossManagerImpl.whiteDomain.contains(domain))
                                continue;
                        } catch (NullPointerException e) {
                            continue;
                        }
                        PunishOperationHandler.punishPlayer((EntityPlayerMP) player, 32, "检测到玩家携带其他模组物品，尝试掉落整个背包物品");
                        break;
                    }
                }
            }
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
