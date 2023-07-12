package com.xwm.magicmaid2.common.world.gen;

import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Random;

public class StructureRuinForestStructurePieces
{
    private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(false);
    private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(false).setReplacedBlock(Blocks.AIR);

    public static void startRuinForest(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructureComponent> componentList, Random random)
    {
        RuinForestTemplate structureRuinForestPieces$ruintemplate = addHelper(componentList, new RuinForestTemplate(templateManager, "altar", pos.down(7), Rotation.NONE, true));
        StructureRuinForestStructurePieces.addHelper(componentList, StructureRuinForestStructurePieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(30, 8, 0), "dedication_exp_pool", Rotation.CLOCKWISE_180, true));
        StructureRuinForestStructurePieces.addHelper(componentList, StructureRuinForestStructurePieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(60, 6, 5), "dedication_life_pool", Rotation.CLOCKWISE_180, true));
        StructureRuinForestStructurePieces.addHelper(componentList, StructureRuinForestStructurePieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(50, 8, 30), "sacrifice_pool", Rotation.CLOCKWISE_180, true));
        StructureRuinForestStructurePieces.addHelper(componentList, StructureRuinForestStructurePieces.addPiece(templateManager, structureRuinForestPieces$ruintemplate, new BlockPos(60, 7, 25), "maier_hut", Rotation.NONE, true));
    }

    private static RuinForestTemplate addHelper(List<StructureComponent> componentList, RuinForestTemplate template) {
        componentList.add(template);
        return template;
    }

    private static RuinForestTemplate addPiece(TemplateManager templateManager, RuinForestTemplate template, BlockPos pos, String name, Rotation rotation, boolean owerwrite) {
        RuinForestTemplate structureechurchcitypieces$churchtemplate = new RuinForestTemplate(templateManager, name, template.getTemplatePos(), rotation, owerwrite);
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
                PlacementSettings placementsettings = (this.overwrite ? OVERWRITE : INSERT).copy().setRotation(this.rotation);
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
