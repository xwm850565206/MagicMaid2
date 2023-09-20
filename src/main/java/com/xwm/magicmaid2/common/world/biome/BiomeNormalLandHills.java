package com.xwm.magicmaid2.common.world.biome;

import com.xwm.magicmaid2.common.entity.mob.EntityLostEvil;
import com.xwm.magicmaid2.common.entity.mob.EntityLostHeart;
import com.xwm.magicmaid2.common.world.gen.WorldGenEvilTrees;
import com.xwm.magicmaid2.common.world.gen.WorldGenNormalLandHillStructure;
import com.xwm.magicmaid2.common.world.gen.WorldGenNormalLandPlainStructure;
import com.xwm.magicmaid2.core.init.BlockInit;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeNormalLandHills extends BiomeHills
{
    private WorldGenNormalLandHillStructure HILL_STRUCTURE = new WorldGenNormalLandHillStructure();

    public BiomeNormalLandHills() {
        super(Type.EXTRA_TREES, new BiomeProperties("normal_land_hills")
                .setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.4F).setRainfall(0.3F));

        this.topBlock = BlockInit.BLOCK_EVIL_GRASS.getDefaultState();
        this.fillerBlock = BlockInit.BLOCK_EVIL_DIRT.getDefaultState();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityLostHeart.class, 20, 2, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityLostEvil.class, 1, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 1, 4));

    }


    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return new WorldGenEvilTrees(false);
    }

    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        super.decorate(worldIn, rand, pos);

        if (rand.nextDouble() < 0.4) {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 48);
            for (int t = l; t < 128; t++) {
                if (worldIn.getBlockState(pos.add(j, t, k)).getBlock() == BlockInit.BLOCK_EVIL_GRASS) {
                    boolean flag = true;
                    for (int ii = 0; ii < 4 && flag; ii++)
                        for (int jj = 0; jj < 4 && flag; jj++)
                        {
                            if (worldIn.getBlockState(pos.add(j+ii, t, k + jj)).getBlock() != BlockInit.BLOCK_EVIL_GRASS)
                                flag = false;
                        }

                    if (flag) {
                         HILL_STRUCTURE.generate(worldIn, rand, pos.add(j, t-5, k));
                    }
                    break;
                }
            }
        }
    }
}
