package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.init.BlockInit;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.common.tileentity.TileEntityForestPortal;
import com.xwm.magicmaid2.common.world.dimension.ForestTeleporter;
import com.xwm.magicmaid2.core.init.DimensionInit;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockForestPortal extends BlockContainer implements IRegistrable {
    protected static final AxisAlignedBB CHURCH_PORTAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public BlockForestPortal(String name)
    {
        super(Material.AIR, MapColor.BLACK);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(1.0F);

        doRegister();
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityForestPortal();
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CHURCH_PORTAL_AABB;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && entityIn.getEntityBoundingBox().intersects(state.getBoundingBox(worldIn, pos).offset(pos)))
        {
            entityIn.setPortal(pos);
            entityIn.changeDimension(DimensionInit.DIMENSION_RUIN_FOREST, new ForestTeleporter(FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(DimensionInit.DIMENSION_RUIN_FOREST)));
        }
    }


    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
//        double d0 = (double)((float)pos.getX() + rand.nextFloat());
//        double d1 = (double)((float)pos.getY() + 0.8F);
//        double d2 = (double)((float)pos.getZ() + rand.nextFloat());
//        double d3 = 0.0D;
//        double d4 = 0.0D;
//        double d5 = 0.0D;
//        ParticleSpawner.spawnParticle(EnumCustomParticles.SOUL, d0, d1, d2, d0 + rand.nextDouble(), d1 + rand.nextDouble(), d2+rand.nextDouble());
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return ItemStack.EMPTY;
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return MapColor.BLACK;
    }

    /**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     *
     * @return an approximation of the form of the given face
     */
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
    }
}
