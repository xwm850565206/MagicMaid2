package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid2.common.world.dimension.DimensionNormalLand;
import com.xwm.magicmaid2.common.world.dimension.DimensionRuinForest;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionInit
{
    public static int DIMENSION_RUIN_FOREST = findFreeDimensionID();

    public static int DIMENSION_NORMAL_LAND = DIMENSION_RUIN_FOREST + 1;

    private static Integer findFreeDimensionID() {
        for (int i = 2; i < Integer.MAX_VALUE; i++) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                return i;
            }
        }
        return null;
    }
    public static final DimensionType RUIN_FOREST = DimensionType.register("ruin_forest", Reference.MODID, DIMENSION_RUIN_FOREST, DimensionRuinForest.class, false);
    public static final DimensionType NORMAL_LAND = DimensionType.register("normal_land", Reference.MODID, DIMENSION_NORMAL_LAND, DimensionNormalLand.class, false);

    public static void registerDimensions()
    {
        DimensionManager.registerDimension(DIMENSION_RUIN_FOREST, RUIN_FOREST);
        DimensionManager.registerDimension(DIMENSION_NORMAL_LAND, NORMAL_LAND);
    }

    public static void registerWorldGenerators()
    {
//        GameRegistry.registerWorldGenerator(new WorldGenHolyStone(), 10);
//        GameRegistry.registerWorldGenerator(new WorldGenFlowers(), 10);
//        GameRegistry.registerWorldGenerator(new WorldGenHut(), 1);
    }
}
