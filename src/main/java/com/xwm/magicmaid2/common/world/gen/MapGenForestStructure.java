package com.xwm.magicmaid2.common.world.gen;

import com.xwm.magicmaid2.common.world.dimension.ChunkGeneratorRuinForest;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.Random;

public class MapGenForestStructure extends MapGenStructure
{
    private final ChunkGeneratorRuinForest provider;
    public MapGenForestStructure(ChunkGeneratorRuinForest chunkGeneratorRuinForest)
    {
        this.provider = chunkGeneratorRuinForest;
    }

    public String getStructureName()
    {
        return "RuinForestStructure";
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, 6, 5, 10387313, true, 100, findUnexplored);
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        if (chunkX == 4 && chunkZ == 10)
            return true;
        return false;
//        return true;
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenForestStructure.Start(this.world, this.provider, this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        private boolean isSizeable;

        public Start()
        {
        }

        public Start(World worldIn, ChunkGeneratorRuinForest chunkProvider, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            this.create(worldIn, chunkProvider, random, chunkX, chunkZ);
        }

        private void create(World worldIn, ChunkGeneratorRuinForest chunkProvider, Random rnd, int chunkX, int chunkZ)
        {
            Random random = new Random((long)(chunkX + chunkZ * 10387313));
            Rotation rotation = Rotation.NONE;

            BlockPos blockpos = new BlockPos(chunkX * 16 + 8, 50, chunkZ * 16 + 8);
            StructureRuinForestStructurePieces.startRuinForest(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components, rnd);
            this.updateBoundingBox();
            this.isSizeable = true;

        }

        /**
         * currently only defined for Villages, returns true if Village has more than 2 non-road components
         */
        public boolean isSizeableStructure()
        {
            return this.isSizeable;
        }
    }
}
