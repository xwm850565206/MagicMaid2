package com.xwm.magicmaid2.common.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenEvilBush extends WorldGenerator
{
    private IBlockState state;

    public void setState(IBlockState state) {
        this.state = state;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        boolean flag = false;

        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            for (int x = -1; x <= 1; x++)
                for (int z = -1; z<= 1; z++)
                {
                    blockpos = blockpos.add(x, 0, z);
                    if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 254) && state.getBlock().canPlaceBlockAt(worldIn, blockpos))
                    {
                        worldIn.setBlockState(blockpos, state, 2);
                        flag = true;
                    }
                }
        }

        return flag;
    }
}
