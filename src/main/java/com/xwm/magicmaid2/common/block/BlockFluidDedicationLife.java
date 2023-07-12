package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.entity.SPacketEntityData;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockFluidDedicationLife extends BlockFluidClassic implements IRegistrable
{
    public BlockFluidDedicationLife(String name, Fluid fluid, Material material) {
        super(fluid, material);

        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setTickRandomly(true);
        this.setTickRate(80);

        doRegister();
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        super.updateTick(world, pos, state, rand);
        if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update

        if (world.isRemote)
            return;

        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityLivingBase> entityLivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        for (EntityLivingBase entityLivingBase : entityLivingBases)
        {
            if (entityLivingBase instanceof EntityPlayerMP) {
                boolean flag1 = false, flag2 = false;
                if (((EntityPlayerMP) entityLivingBase).isCreative())
                    continue;



                if (!entityLivingBase.getEntityData().hasKey("dedication_life") || !entityLivingBase.getEntityData().getBoolean("dedication_life")) {
                    entityLivingBase.getEntityData().setBoolean("dedication_life", true);
                    entityLivingBase.sendMessage(new TextComponentString("濯涤心灵，去除善恶，纯粹的灵魂能得到祂的青睐..."));

                    if (getFeedNumber((EntityPlayerMP) entityLivingBase) > 10) {
                        SPacketEntityData packet = new SPacketEntityData(entityLivingBase.getEntityId(), world.provider.getDimension(), 1, "1", "dedication_life_type");
                        NetworkLoader.instance.sendToAll(packet);
                    }
                    else if (getKillNumber((EntityPlayerMP) entityLivingBase) > 20) {
                        SPacketEntityData packet = new SPacketEntityData(entityLivingBase.getEntityId(), world.provider.getDimension(), 1, "2", "dedication_life_type");
                        NetworkLoader.instance.sendToAll(packet);
                    }
                    else {
                        SPacketEntityData packet = new SPacketEntityData(entityLivingBase.getEntityId(), world.provider.getDimension(), 1, "0", "dedication_life_type");
                        NetworkLoader.instance.sendToAll(packet);
                    }

                    SPacketEntityData packet = new SPacketEntityData(entityLivingBase.getEntityId(), world.provider.getDimension(), 0, "true", "dedication_life");
                    NetworkLoader.instance.sendToAll(packet);
                    continue;
                }



                if (getKillNumber((EntityPlayerMP) entityLivingBase) > 20)
                {
                    decreaseKillNum((EntityPlayerMP) entityLivingBase, 20);
                    EntityItem entityItem = new EntityItem(world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, new ItemStack(com.xwm.magicmaid.init.ItemInit.ITEM_EVIL_SOUL_PIECE));
                    entityItem.setDefaultPickupDelay();
                    world.spawnEntity(entityItem);
                    flag1 = true;
                    entityLivingBase.sendMessage(new TextComponentString("祂说,除去恶"));
                }
                if (getFeedNumber((EntityPlayerMP) entityLivingBase) > 10){
                    decreaseFeedNum((EntityPlayerMP) entityLivingBase, 10);
                    EntityItem entityItem = new EntityItem(world, entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, new ItemStack(com.xwm.magicmaid.init.ItemInit.ITEM_JUSTICE_SOUL_PIECE));
                    entityItem.setDefaultPickupDelay();
                    world.spawnEntity(entityItem);
                    flag2 = true;
                    entityLivingBase.sendMessage(new TextComponentString("祂说,除去善"));
                }

                if (!flag1 && !flag2) {
                    entityLivingBase.getCombatTracker().trackDamage(DamageSource.MAGIC, entityLivingBase.getHealth(), 1);
                    entityLivingBase.onKillCommand();
                    world.setBlockState(entityLivingBase.getPosition(), BlockInit.BLOCK_DEDICATION_DEAD.getDefaultState(), 3);
                    entityLivingBase.sendMessage(new TextComponentString("祂说,天堂..."));
                }
                else {
                    entityLivingBase.getEntityData().setBoolean("dedication_life", false);
                }


            }
            else {
                entityLivingBase.attackEntityFrom(DamageSource.MAGIC.setMagicDamage().setDamageBypassesArmor(), 4);
            }
        }
        world.scheduleUpdate(pos, this, this.tickRate(world));

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH,
                pos.getX() + rand.nextDouble(),
                pos.getY() + rand.nextDouble(),
                pos.getZ() + rand.nextDouble(),
                0, 0, 0);
    }

    public int getKillNumber(EntityPlayerMP player)
    {
        return player.getStatFile().readStat(StatList.MOB_KILLS);
    }

    public int getFeedNumber(EntityPlayerMP player)
    {
        return player.getStatFile().readStat(StatList.ANIMALS_BRED);
    }

    @SideOnly(Side.CLIENT)
    public int getKillNumber(EntityPlayerSP player)
    {
        return player.getStatFileWriter().readStat(StatList.MOB_KILLS);
    }

    @SideOnly(Side.CLIENT)
    public int getFeedNumber(EntityPlayerSP player)
    {
        return player.getStatFileWriter().readStat(StatList.ANIMALS_BRED);
    }

    public void decreaseKillNum(EntityPlayerMP player, int num)
    {
        player.getStatFile().increaseStat(player, StatList.MOB_KILLS, -1*num);
    }

    public void decreaseFeedNum(EntityPlayerMP player, int num)
    {
        player.getStatFile().increaseStat(player, StatList.ANIMALS_BRED, -1*num);
    }

    @SideOnly(Side.CLIENT)
    public void decreaseKillNum(EntityPlayerSP player, int num)
    {
        player.getStatFileWriter().increaseStat(player, StatList.MOB_KILLS, -1*num);
    }

    @SideOnly(Side.CLIENT)
    public void decreaseFeedNum(EntityPlayerSP player, int num)
    {
        player.getStatFileWriter().increaseStat(player, StatList.ANIMALS_BRED, -1*num);
    }




}
