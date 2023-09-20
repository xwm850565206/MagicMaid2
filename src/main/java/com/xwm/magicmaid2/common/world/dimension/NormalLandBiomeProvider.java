package com.xwm.magicmaid2.common.world.dimension;

import com.google.common.collect.Lists;
import com.xwm.magicmaid2.core.init.BiomeInit;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class NormalLandBiomeProvider extends BiomeProvider
{

    public NormalLandBiomeProvider(World world) {
        super(world.getWorldInfo());
        allowedBiomes = Lists.newArrayList(BiomeInit.NORMAL_LAND_PLAIN, BiomeInit.NORMAL_LAND_HILLS);
        getBiomesToSpawnIn().clear();
        getBiomesToSpawnIn().add(BiomeInit.NORMAL_LAND_PLAIN);
        getBiomesToSpawnIn().add(BiomeInit.NORMAL_LAND_HILLS);

        makeLayers(world.getSeed());
    }

    private GenLayer[] makeLayers(long seed)
    {
        GenLayer biomes = new GenLayerNormalLandBiomes(1L);

        biomes = new GenLayerZoom(1000L, biomes);
        biomes = new GenLayerZoom(1001, biomes);

        biomes = new GenLayerZoom(1002, biomes);
        biomes = new GenLayerZoom(1003, biomes);
        biomes = new GenLayerZoom(1004, biomes);
        biomes = new GenLayerZoom(1005, biomes);

        // do "voronoi" zoom
        GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

        biomes.initWorldGenSeed(seed);
        genlayervoronoizoom.initWorldGenSeed(seed);

        return new GenLayer[]{
                biomes,
                genlayervoronoizoom
        };
    }

    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {

        return makeLayers(seed);
    }


    public static class GenLayerNormalLandBiomes extends GenLayer {

        protected static final List<Supplier<Biome>> commonBiomes = Arrays.asList(
                () -> BiomeInit.NORMAL_LAND_PLAIN,
                () -> BiomeInit.NORMAL_LAND_HILLS
        );


        public GenLayerNormalLandBiomes(long l, GenLayer genlayer) {
            super(l);
            parent = genlayer;
        }

        public GenLayerNormalLandBiomes(long l) {
            super(l);
        }

        @Override
        public int[] getInts(int x, int z, int width, int depth) {

            int dest[] = IntCache.getIntCache(width * depth);

            for (int dz = 0; dz < depth; dz++) {
                for (int dx = 0; dx < width; dx++) {
                    initChunkSeed(dx + x, dz + z);
                    dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(commonBiomes));
                }
            }
            return dest;
        }

        private Biome getRandomBiome(List<Supplier<Biome>> biomes) {
            return biomes.get(nextInt(biomes.size())).get();
        }
    }
}
