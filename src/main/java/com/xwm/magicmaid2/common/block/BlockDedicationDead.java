package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.object.item.ItemSkillBook;
import com.xwm.magicmaid.registry.MagicSkillRegistry;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.common.tileentity.TileEntityDedicationDead;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.DimensionInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockDedicationDead extends BlockContainer implements IRegistrable
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockDedicationDead(String name) {
        super(Material.ROCK, MapColor.GRAY);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 4);
        this.setTickRandomly(true);

        doRegister();
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
//        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDedicationDead();
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        boolean flag = checkIsInSacrifice(worldIn, pos);
        if (flag) {
            worldIn.scheduleUpdate(pos, BlockInit.BLOCK_DEDICATION_DEAD, 60);
        }
        else {

        }
    }


    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (checkIsInSacrifice(worldIn, pos)) {
            if (!worldIn.isRemote) {
                EntityItem entityItem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), getSacrificeItem(worldIn, pos, rand));
                entityItem.setDefaultPickupDelay();
                worldIn.spawnEntity(entityItem);

                EntityLightningBolt bolt = new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), true);
                worldIn.addWeatherEffect(bolt);
                worldIn.spawnEntity(bolt);

                if (worldIn instanceof WorldServer)
                {
                    ((WorldServer)worldIn).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX(), pos.getY(), pos.getZ(), 10, rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0.1);
                }
            }
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    public static boolean checkIsInSacrifice(World world, BlockPos pos)
    {
        if (world.provider.getDimension() != DimensionInit.DIMENSION_RUIN_FOREST)
            return false;

        if (world.getBlockState(pos.west()).getBlock() != Blocks.COBBLESTONE)
            return false;

        if (world.getBlockState(pos.east()).getBlock() != Blocks.COBBLESTONE)
            return false;

        if (world.getBlockState(pos.south()).getBlock() != Blocks.COBBLESTONE)
            return false;

        if (world.getBlockState(pos.north()).getBlock() != Blocks.AIR)
            return false;

        if (world.getBlockState(pos.down()).getBlock() != Blocks.GLASS)
            return false;

        if (world.getBlockState(pos.up()).getBlock() != Blocks.AIR)
            return false;

        return true;
    }

    public static ItemStack getSacrificeItem(World world, BlockPos pos, Random random)
    {
        EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 2, false);
        if (player instanceof EntityPlayerMP) {
            StatBase statBase = StatList.getObjectsPickedUpStats(com.xwm.magicmaid.init.ItemInit.SKILL_BOOK);
            if (statBase != null) {
                int cnt = ((EntityPlayerMP) player).getStatFile().readStat(statBase);
                if (cnt < 2) {
                    ItemStack skillBook = new ItemStack(com.xwm.magicmaid.init.ItemInit.SKILL_BOOK);
                    List<String> performSkills = new ArrayList<String>() {{
                        MagicSkillRegistry.SKILL_MAP.forEach((name, clazz) -> {
                            if (name.contains("perform") && !name.contains("unreachable"))
                                this.add(name);
                        });
                    }};
                    ItemSkillBook.setSkill(skillBook, performSkills.get(random.nextInt(performSkills.size())));
                    return skillBook;
                }
            }
        }
        float p = random.nextFloat();
        if (p < 0.01f) {
            return new ItemStack(ItemInit.EVIL_INGOT);
        }
        else if (p < 0.05f) {
            ItemStack skillBook = new ItemStack(com.xwm.magicmaid.init.ItemInit.SKILL_BOOK);
            List<String> performSkills = new ArrayList<String>(){{
                MagicSkillRegistry.SKILL_MAP.forEach((name, clazz) -> {
                    if (name.contains("perform") && !name.contains("unreachable"))
                        this.add(name);
                });
            }};

            ItemSkillBook.setSkill(skillBook, performSkills.get(random.nextInt(performSkills.size())));
            return skillBook;
        }
        else if (p < 0.25f) {
            return new ItemStack(com.xwm.magicmaid.init.ItemInit.ITEM_HOLY_STONE, random.nextInt(32));
        }
        else if (p < 0.85f) {
            return new ItemStack(com.xwm.magicmaid.init.ItemInit.ITEM_HOLY_STONE, 1);
        }
        else {
            return new ItemStack(Items.BAKED_POTATO);
        }

    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        try {
            return ((EnumFacing) state.getValue(FACING)).getIndex();
        } catch (Exception e) {
            return EnumFacing.NORTH.getIndex(); // 不知道为啥这边报了一次错误，所以直接catch返回默认值
        }
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemInit.DEDICATION_DEAD_ITEM;
    }

}
