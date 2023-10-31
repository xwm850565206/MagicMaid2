package com.xwm.magicmaid2.common.world.biome;

import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeletonMaster;
import com.xwm.magicmaid2.common.entity.mob.EntityLostEvil;
import com.xwm.magicmaid2.common.entity.mob.EntityLostHeart;
import com.xwm.magicmaid2.common.world.gen.WorldGenEvilBush;
import com.xwm.magicmaid2.common.world.gen.WorldGenEvilPlant;
import com.xwm.magicmaid2.common.world.gen.WorldGenNormalLandBushStructure;
import com.xwm.magicmaid2.common.world.gen.WorldGenNormalLandPlainStructure;
import com.xwm.magicmaid2.core.init.BlockInit;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomePlains;

import java.util.Random;

public class BiomeNormalLandBush extends BiomePlains
{

    private WorldGenEvilBush BUSH_GENERATOR = new WorldGenEvilBush();

    private WorldGenNormalLandBushStructure BUSH_STRUCTURE = new WorldGenNormalLandBushStructure();

    public BiomeNormalLandBush() {
        super(false, new BiomeProperties("normal_land_bush")
                .setBaseHeight(0.125f)
                .setHeightVariation(0.05f)
                .setTemperature(0.5f)
                .setRainfall(0.8f)
                .setWaterColor(0xDDDDDD));
        this.topBlock = BlockInit.BLOCK_EVIL_GRASS.getDefaultState();
        this.fillerBlock = BlockInit.BLOCK_EVIL_DIRT.getDefaultState();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityEvilSkeletonMaster.class, 10, 1, 2));

    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {

        double d0 = GRASS_COLOR_NOISE.getValue((double)(pos.getX() + 8) / 200.0D, (double)(pos.getZ() + 8) / 200.0D);

        if (d0 < -0.8D)
        {
            this.decorator.flowersPerChunk = 15;
            this.decorator.grassPerChunk = 5;
        }
        else
        {
            this.decorator.flowersPerChunk = 4;
            this.decorator.grassPerChunk = 10;

            BUSH_GENERATOR.setState(BlockInit.BLOCK_EVIL_BUSH.getDefaultState());
            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
                for (int i = 0; i < 7; ++i) {
                    int j = rand.nextInt(16) + 8;
                    int k = rand.nextInt(16) + 8;
                    int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
                    BUSH_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
                }
        }

        if (rand.nextDouble() < 0.1) {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
            for (int t = l; t < 64; t++) {
                boolean flag = true;
                for (int ii = 0; ii < 4 && flag; ii++)
                    for (int jj = 0; jj < 4 && flag; jj++)
                    {
                        if (worldIn.getBlockState(pos.add(j+ii, t, k + jj)).getBlock() != BlockInit.BLOCK_EVIL_GRASS || worldIn.getBlockState(pos.add(j+ii, t+1, k + jj)).getBlock() != Blocks.AIR)
                            flag = false;
                    }

                if (flag) {
                    BUSH_STRUCTURE.generate(worldIn, rand, pos.add(j, t, k));
                    break;
                }
            }
        }

        super.decorate(worldIn, rand, pos);
    }

    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance()
    {
        return 0.001F;
    }

    @Override
    public void addDefaultFlowers()
    {
        super.addDefaultFlowers();
    }
}