package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid2.common.world.biome.BiomeFogForest;
import com.xwm.magicmaid2.common.world.biome.BiomeNormalLandHills;
import com.xwm.magicmaid2.common.world.biome.BiomeNormalLandPlain;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeInit
{
    public static final Biome FOG_FOREST = new BiomeFogForest();
    public static final Biome NORMAL_LAND_PLAIN = new BiomeNormalLandPlain();

    public static final Biome NORMAL_LAND_HILLS = new BiomeNormalLandHills();

    public static void registerBiomes()
    {
        initBiome(FOG_FOREST, "fog_forest", BiomeManager.BiomeType.WARM, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.WET);
        initBiome(NORMAL_LAND_PLAIN, "normal_land_plain", BiomeManager.BiomeType.COOL, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.WET);
        initBiome(NORMAL_LAND_HILLS, "normal_land_hills", BiomeManager.BiomeType.COOL, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.WET);
    }

    private static Biome initBiome(Biome biome, String name, BiomeManager.BiomeType biomeType, BiomeDictionary.Type... type)
    {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, type);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, 1));
        return biome;
    }
}
