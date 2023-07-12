package com.xwm.magicmaid2.common.world.gen;

import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.resources.IResource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Random;

public class StructureRuinForestPieces
{
    private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(true);
    private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);


    public static void registerPieces()
    {
        MapGenStructureIO.registerStructureComponent(StructureRuinForestPieces.RuinForestTemplate.class, "RuinForestComponent");
        MapGenStructureIO.registerStructure(MapGenRuinForest.Start.class, "RuinForest");
        MapGenStructureIO.registerStructureComponent(StructureRuinForestStructurePieces.RuinForestTemplate.class, "RuinForestStructureComponent");
        MapGenStructureIO.registerStructure(MapGenForestStructure.Start.class, "RuinForestStructure");
//        MapGenStructureIO.registerStructure(MapGenRuinForestSt.Start.class, "RuinForest");
    }

    public static void startRuinForest(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructureComponent> componentList, Random random)
    {
        StructureRuinForestPieces.RuinForestTemplate structureRuinForestPieces$ruintemplate = addHelper(componentList, new StructureRuinForestPieces.RuinForestTemplate(templateManager, "ruin_front_left", pos, Rotation.NONE, true));
//        structureRuinForestPieces$ruintemplate =
        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(22, 0, 0), "ruin_front_right", Rotation.NONE, true));
//        structureRuinForestPieces$ruintemplate =
        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(0, 0, -22), "ruin_rear_left", Rotation.NONE, true));
//        structureRuinForestPieces$ruintemplate =
        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(22, 0, -22), "ruin_rear_right", Rotation.NONE, true));

//        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(20, -8, 100), "altar", Rotation.NONE, true));
//        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(90, 1, 100), "dedication_exp_pool", Rotation.CLOCKWISE_180, true));
//        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(130, -1, 105), "dedication_life_pool", Rotation.CLOCKWISE_180, true));
//        StructureRuinForestPieces.addHelper(componentList, StructureRuinForestPieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(110, 1, 125), "sacrifice_pool", Rotation.CLOCKWISE_180, true));

    }

    private static StructureRuinForestPieces.RuinForestTemplate addHelper(List<StructureComponent> componentList, StructureRuinForestPieces.RuinForestTemplate template) {
        componentList.add(template);
        return template;
    }

    private static StructureRuinForestPieces.RuinForestTemplate addPiece(TemplateManager templateManager, StructureRuinForestPieces.RuinForestTemplate template, BlockPos pos, String name, Rotation rotation, boolean owerwrite) {
        StructureRuinForestPieces.RuinForestTemplate structureechurchcitypieces$churchtemplate = new StructureRuinForestPieces.RuinForestTemplate(templateManager, name, template.getTemplatePos(), rotation, owerwrite);
        structureechurchcitypieces$churchtemplate.offset(pos.getX(), pos.getY(), pos.getZ());
        return structureechurchcitypieces$churchtemplate;
    }


    public static class RuinForestTemplate extends StructureComponentTemplate
    {
        private String pieceName;
        private Rotation rotation;
        /** Whether this template should overwrite existing blocks. Replaces only air if false. */
        private boolean overwrite;

        public RuinForestTemplate() {
            super(0);
        }

        public RuinForestTemplate(TemplateManager templateManager, String pieceName, BlockPos blockPos, Rotation rotation, boolean overwriteIn)
        {
            super(0);
            this.pieceName = pieceName;
            this.templatePosition = blockPos;
            this.rotation = rotation;
            this.overwrite = overwriteIn;
            this.loadTemplate(templateManager);
        }

        private void loadTemplate(TemplateManager templateManager)
        {
            try{
                Template template = templateManager.getTemplate(null, new ResourceLocation(Reference.MODID, this.pieceName));
                PlacementSettings placementsettings = (this.overwrite ? StructureRuinForestPieces.OVERWRITE : StructureRuinForestPieces.INSERT).copy().setRotation(this.rotation);
                this.setup(template, this.templatePosition, placementsettings);
            } catch (NullPointerException e){
                e.printStackTrace();
            }

        }

        protected void writeStructureToNBT(NBTTagCompound tagCompound)
        {
            super.writeStructureToNBT(tagCompound);

            if (pieceName == null) pieceName = "undefined";
            if (rotation == null) rotation = Rotation.NONE;

            tagCompound.setString("Template", this.pieceName);
            tagCompound.setString("Rot", this.rotation.name());
            tagCompound.setBoolean("OW", this.overwrite);
//            tagCompound.setLong("pos", this.templatePosition.toLong());
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager)
        {
            super.readStructureFromNBT(tagCompound, templateManager);
            this.pieceName = tagCompound.getString("Template");
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.overwrite = tagCompound.getBoolean("OW");
//            this.templatePosition = BlockPos.fromLong(tagCompound.getLong("pos"));
            if (pieceName == null) pieceName = "undefined";
            if  (rotation == null) rotation = Rotation.NONE;

            this.loadTemplate(templateManager);
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {
            if (function.startsWith("tramp_chest"))
            {
                BlockPos blockpos = pos;
                worldIn.setBlockState(blockpos, Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST), 3);
                TileEntity tileentity = worldIn.getTileEntity(blockpos);
                if (tileentity instanceof TileEntityChest)
                {
                    ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_JUNGLE_TEMPLE, rand.nextLong());
                }

            }
            else if (function.startsWith("memory_clock"))
            {
                BlockPos blockpos = pos;
                worldIn.setBlockState(blockpos, BlockInit.BLOCK_MEMORY_CLOCK.getDefaultState(), 3);
            }
            else if (function.startsWith("brew_chest"))
            {
                BlockPos blockpos = pos;
                worldIn.setBlockState(blockpos, Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH), 3);
                TileEntity tileentity = worldIn.getTileEntity(blockpos);
                if (tileentity instanceof TileEntityChest)
                {
                    IItemHandler handler = ((TileEntityChest) tileentity).getSingleChestHandler();
                    handler.insertItem(0, new ItemStack(ItemInit.ITEM_EVIL, 3), worldIn.isRemote);
                    handler.insertItem(1, new ItemStack(ItemInit.ITEM_JUSTICE, 3), worldIn.isRemote);
                    handler.insertItem(2, Main.proxy.getWrittenBook("diary_1"), worldIn.isRemote);
                    handler.insertItem(3, Main.proxy.getWrittenBook("diary_2"), worldIn.isRemote);
                    handler.insertItem(4, Main.proxy.getWrittenBook("diary_3"), worldIn.isRemote);
                    handler.insertItem(5, Main.proxy.getWrittenBook("diary_4"), worldIn.isRemote);
                }

            }
        }

        public Template getTemplate(){
            return this.template;
        }

        public PlacementSettings getPlacementSettings() {
            return this.placeSettings;
        }

        public BlockPos getTemplatePos(){
            return this.templatePosition;
        }

        public void setComponentType(int componentType){
            this.componentType = componentType;
        }

        public int getComponentType(){
            return this.componentType;
        }
    }
}
