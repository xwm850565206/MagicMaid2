package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.object.block.BlockBase;
import com.xwm.magicmaid2.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockEvilGrass extends BlockBase implements IGrowable
{

    public BlockEvilGrass(String name, Material material) {
        super(name, material);
        this.setTickRandomly(true);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        Set<Block> WHITE_BLOCK = new HashSet<Block>(){{
            this.add(BlockInit.BLOCK_EVIL_TALL_GRASS);
        }};

        IBlockState plantState = plantable.getPlant(world, pos);
        if (WHITE_BLOCK.contains(plantState.getBlock()) && direction == EnumFacing.UP)
        {
            return true;
        }
        else
            return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        BlockPos blockpos = pos.up();

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos1 = blockpos;
            int j = 0;

            while (true)
            {
                if (j >= i / 16)
                {
                    if (worldIn.isAirBlock(blockpos1))
                    {
                        if (rand.nextInt(8) == 0)
                        {
                            worldIn.getBiome(blockpos1).plantFlower(worldIn, rand, blockpos1);
                        }
                        else
                        {
                            worldIn.setBlockState(blockpos1, BlockInit.BLOCK_EVIL_TALL_GRASS.getDefaultState(), 3);

                        }
                    }

                    break;
                }

                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if (worldIn.getBlockState(blockpos1.down()).getBlock() != BlockInit.BLOCK_EVIL_GRASS || worldIn.getBlockState(blockpos1).isNormalCube())
                {
                    break;
                }

                ++j;
            }
        }


    }
}
