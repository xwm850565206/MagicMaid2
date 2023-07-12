package com.xwm.magicmaid2.common.world.biome;

import com.xwm.magicmaid2.common.entity.mob.EntityLostEvil;
import com.xwm.magicmaid2.common.entity.mob.EntityLostHeart;
import com.xwm.magicmaid2.common.world.gen.WorldGenEvilPlant;
import com.xwm.magicmaid2.common.world.gen.WorldGenNormalLandPlainStructure;
import com.xwm.magicmaid2.core.init.BlockInit;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomePlains;

import java.util.Random;

public class BiomeNormalLandPlain extends BiomePlains
{

    private WorldGenEvilPlant EVIL_PLANT_GENERATOR = new WorldGenEvilPlant();

    private WorldGenNormalLandPlainStructure PLAIN_STRUCTURE = new WorldGenNormalLandPlainStructure();

    public BiomeNormalLandPlain() {
        super(false, new BiomeProperties("normal_land_plain")
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
        this.spawnableMonsterList.add(new SpawnListEntry(EntityLostHeart.class, 20, 2, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityLostEvil.class, 1, 1, 1));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 1, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 2, 2, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 1, 1, 6));

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

            EVIL_PLANT_GENERATOR.setState(BlockInit.BLOCK_EVIL_TALL_GRASS.getDefaultState());
            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
                for (int i = 0; i < 7; ++i)
                {
                    int j = rand.nextInt(16) + 8;
                    int k = rand.nextInt(16) + 8;
                    int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
                    EVIL_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
                }

            EVIL_PLANT_GENERATOR.setState(BlockInit.BLOCK_FLOWER_EVIL.getDefaultState());
            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
                for (int i = 0; i < 2; ++i)
                {
                    int j = rand.nextInt(16) + 8;
                    int k = rand.nextInt(16) + 8;
                    int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
                    EVIL_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
                }
        }

        if (rand.nextDouble() < 0.1) {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
            for (int t = l; t < 64; t++) {
                if (worldIn.getBlockState(pos.add(j, t, k)).getBlock() == BlockInit.BLOCK_EVIL_GRASS) {
                    boolean flag = true;
                    for (int ii = 0; ii < 4 && flag; ii++)
                        for (int jj = 0; jj < 4 && flag; jj++)
                        {
                            if (worldIn.getBlockState(pos.add(j+ii, t, k + jj)).getBlock() != BlockInit.BLOCK_EVIL_GRASS)
                                flag = false;
                        }

                    if (flag) {
                        PLAIN_STRUCTURE.generate(worldIn, rand, pos.add(j, t, k));
                    }
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