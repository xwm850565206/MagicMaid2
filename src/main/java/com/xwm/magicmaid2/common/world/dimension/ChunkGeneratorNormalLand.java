package com.xwm.magicmaid2.common.world.dimension;

import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGeneratorFlat;
import net.minecraft.world.gen.ChunkGeneratorOverworld;

public class ChunkGeneratorNormalLand extends ChunkGeneratorOverworld
{

    public ChunkGeneratorNormalLand(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
        super(worldIn, seed, generateStructures, flatGeneratorSettings);
    }
}
