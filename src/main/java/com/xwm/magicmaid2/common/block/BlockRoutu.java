package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.object.block.BlockBase;
import com.xwm.magicmaid2.common.entity.mob.EntityRoutu;
import com.xwm.magicmaid2.common.tileentity.TileEntityCandleStick;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockRoutu extends BlockBase {
    private Random random = new Random();

    public BlockRoutu(String name) {
        super(name, Material.ROCK);
        setTickRandomly(true);
        setHardness(1.5F);
        setResistance(10.0F);
    }


    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (worldIn.isRemote)
            return;
        if (worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false)!= null)
        {
            for (int i = 0; i < 6; i++) {
                EntityRoutu routu = new EntityRoutu(worldIn);
                routu.setPosition(pos.getX() + rand.nextInt(4) - 2, pos.getY(), pos.getZ() + + rand.nextInt(4) - 2);
                worldIn.spawnEntity(routu);
            }

            worldIn.destroyBlock(pos, false);
//            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
        else
            worldIn.scheduleUpdate(pos, this, 20);
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }
}
