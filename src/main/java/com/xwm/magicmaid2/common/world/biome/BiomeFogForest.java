package com.xwm.magicmaid2.common.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;

public class BiomeFogForest extends BiomeForest
{
    public BiomeFogForest() {
        super(Type.NORMAL, new BiomeProperties("fog_forest")
                .setBaseHeight(0.125f)
                .setHeightVariation(0.05f)
                .setTemperature(0.5f)
                .setRainfall(0.8f)
                .setWaterColor(0xDDDDDD));
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 1, 4, 4));
    }

    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance()
    {
        return 0.001F;
    }
}