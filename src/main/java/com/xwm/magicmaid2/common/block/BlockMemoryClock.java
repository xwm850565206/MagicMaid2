package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.Main;
import com.xwm.magicmaid.entity.mob.maid.EntityMagicMaid;
import com.xwm.magicmaid.manager.IMagicBossManager;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.SPacketSound;
import com.xwm.magicmaid.object.block.BlockBase;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidAiliBoss;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCarlieBoss;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCassiuBoss;
import com.xwm.magicmaid2.common.world.dimension.DimensionRuinForest;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockMemoryClock extends BlockBase {
    Random random = new Random();

    public BlockMemoryClock(String name) {
        super(name, Material.IRON);

        setHardness(100.0f);
        setResistance(2000.0f);
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }


    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
            return false;

        if (hand != EnumHand.MAIN_HAND)
            return false;

        if (playerIn.getHeldItem(hand).getItem() == com.xwm.magicmaid.init.ItemInit.ITEM_LOST_KEY)
        {
            IMagicBossManager fightManager = ((DimensionRuinForest) worldIn.provider).getFightManager();
            if (fightManager.getBoss() != null) {
                try {
                    fightManager.setBoss(null);
//                    IMagicCreatureManagerImpl.getInstance().setDead((IEntityAvoidThingCreature) fightManager.getBoss());
                    fightManager.setBossAlive(false);
                    fightManager.setBossKilled(false);
                } catch (Exception e) {
                    Main.logger.info("recovery failed");
                }
                ItemStack stack = playerIn.getHeldItem(hand);
                if (!playerIn.isCreative())
                    stack.shrink(1);
                return true;
            }
        }

        //不在教堂世界，不生成女仆
        if (!(worldIn.provider instanceof DimensionRuinForest)) {
            playerIn.sendMessage(new TextComponentString("记忆铜钟只有在迷雾之森维度敲响才有用"));
            return false;
        }

        //如果boss存在，就不该再生成boss了
        IMagicBossManager fightManager = ((DimensionRuinForest) worldIn.provider).getFightManager();
        if (fightManager.getBossAlive()) {
            playerIn.sendMessage(new TextComponentString("请击杀boss后再敲响记忆铜钟"));
            return false;
        }

        EntityMagicMaid bossMaid;
        int f = random.nextInt(3);
        switch (f){
            case 0:
                bossMaid = new EntityMagicMaidAiliBoss(worldIn);break;
            case 1:
                bossMaid = new EntityMagicMaidCarlieBoss(worldIn);break;
            default:
                bossMaid = new EntityMagicMaidCassiuBoss(worldIn);break;
        }

        bossMaid.setPosition(pos.getX(), pos.getY()+2, pos.getZ());
        worldIn.spawnEntity(bossMaid);
        fightManager.init(bossMaid);

        SPacketSound packet = new SPacketSound(0, pos);
        NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 40.0D);
        NetworkLoader.instance.sendToAllAround(packet, target);

        return true;
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }
}
