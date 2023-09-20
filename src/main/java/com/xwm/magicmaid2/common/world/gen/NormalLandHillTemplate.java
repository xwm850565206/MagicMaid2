package com.xwm.magicmaid2.common.world.gen;

import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid.object.item.ItemSkillBook;
import com.xwm.magicmaid.player.skill.ISkill;
import com.xwm.magicmaid2.common.block.BlockEvilSkeleton;
import com.xwm.magicmaid2.core.init.BlockInit;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Random;

public class NormalLandHillTemplate extends StructureComponentTemplate
{
    private List<ISkill> skillList;
    private int meta;
    private int cnt;

    public NormalLandHillTemplate(List<ISkill> skillList, Template template, BlockPos pos, PlacementSettings settings) {
        this(0, skillList, template, pos, settings);

    }

    public NormalLandHillTemplate(int type, List<ISkill> skillList, Template template, BlockPos pos, PlacementSettings settings) {
        super(type);
        this.skillList = skillList;
        this.setup(template, pos, settings);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb) {
        if (function.equals("planks")) {
            if (cnt == 0) {
                meta = rand.nextInt(6);
                cnt = 4;
            }
            worldIn.setBlockState(pos, Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.byMetadata(meta)));
            cnt--;
        }
        else if(function.equals("evil_skeleton")) {
            if (worldIn.getBlockState(pos.south()).getBlock() == Blocks.PLANKS)
                worldIn.setBlockState(pos, BlockInit.BLOCK_EVIL_SKELETON_BLOCK.getDefaultState().withProperty(BlockEvilSkeleton.FACING, EnumFacing.NORTH));
            else if (worldIn.getBlockState(pos.north()).getBlock() == Blocks.PLANKS)
                worldIn.setBlockState(pos, BlockInit.BLOCK_EVIL_SKELETON_BLOCK.getDefaultState().withProperty(BlockEvilSkeleton.FACING, EnumFacing.SOUTH));
            else if (worldIn.getBlockState(pos.west()).getBlock() == Blocks.PLANKS)
                worldIn.setBlockState(pos, BlockInit.BLOCK_EVIL_SKELETON_BLOCK.getDefaultState().withProperty(BlockEvilSkeleton.FACING, EnumFacing.EAST));
            else
                worldIn.setBlockState(pos, BlockInit.BLOCK_EVIL_SKELETON_BLOCK.getDefaultState().withProperty(BlockEvilSkeleton.FACING, EnumFacing.WEST));
        }
        else if(function.equals("cross")) {
            if (rand.nextFloat() < 0.2)
                worldIn.setBlockState(pos, BlockInit.BLOC_HOLY_CROSS.getDefaultState());
            else
                worldIn.setBlockState(pos, Blocks.TORCH.getDefaultState());
        }
        else if (function.equals("chest")) {
            worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, this.placeSettings.getRotation().rotate(EnumFacing.getFront(2+rand.nextInt(4)))));
            TileEntityChest chest = (TileEntityChest) worldIn.getTileEntity(pos);
            IItemHandler handler = chest.getSingleChestHandler();
            for (int i = 0; i < 1 + rand.nextInt(1); i++) { // 1本书
                ISkill skill = skillList.get(rand.nextInt(skillList.size()));
                ItemStack skillBook = new ItemStack(ItemInit.SKILL_BOOK);
                ItemSkillBook.setSkill(skillBook, skill);
                handler.insertItem(rand.nextInt(27), skillBook, worldIn.isRemote);
            }
            for (int i = 0; i < rand.nextInt(3); i++) {
                Item itemToInsert = Item.getItemById(rand.nextInt(Item.REGISTRY.getKeys().size()));
                ItemStack stack = new ItemStack(itemToInsert);
                int slot = rand.nextInt(27);
                if (handler.getStackInSlot(slot).isEmpty())
                    handler.insertItem(slot, stack, worldIn.isRemote);
            }

            if (rand.nextFloat() < 0.2f) {
                int num = rand.nextInt(4);
                ItemStack stack = new ItemStack(ItemInit.ITEM_JUSTICE_SOUL_PIECE, num);
                int slot = rand.nextInt(27);
                if (handler.getStackInSlot(slot).isEmpty())
                    handler.insertItem(slot, stack, worldIn.isRemote);
            }
        }
    }
}
