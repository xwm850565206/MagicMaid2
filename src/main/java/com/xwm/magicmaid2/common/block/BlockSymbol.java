package com.xwm.magicmaid2.common.block;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.object.item.ItemSkillBook;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid2.common.tileentity.TileEntitySymbol;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.registry.SkillRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockSymbol extends BlockContainer implements IRegistrable, IHasModel
{

    public static final List<ItemStack> SYMBOL_0 = new ArrayList<ItemStack>(){{
        add(new ItemStack(ItemInit.SYMBOL_WHITE));
        add(new ItemStack(ItemInit.SYMBOL_BLACK));
    }};
    public static final List<ItemStack> SYMBOL_1 = new ArrayList<ItemStack>(){{
        add(new ItemStack(ItemInit.SYMBOL_SPRING));
        add(new ItemStack(ItemInit.SYMBOL_SUMMER));
        add(new ItemStack(ItemInit.SYMBOL_AUT));
        add(new ItemStack(ItemInit.SYMBOL_WINTER));
    }};
    public static final List<ItemStack> SYMBOL_2 = new ArrayList<ItemStack>(){{
        add(new ItemStack(ItemInit.SYMBOL_GOLD));
        add(new ItemStack(ItemInit.SYMBOL_GREEN));
        add(new ItemStack(ItemInit.SYMBOL_BLUE));
        add(new ItemStack(ItemInit.SYMBOL_RED));
        add(new ItemStack(ItemInit.SYMBOL_EARTH));
    }};
    public static final List<ItemStack> SYMBOL_3 = new ArrayList<ItemStack>(){{
        add(new ItemStack(ItemInit.SYMBOL_0));
        add(new ItemStack(ItemInit.SYMBOL_1));
        add(new ItemStack(ItemInit.SYMBOL_2));
        add(new ItemStack(ItemInit.SYMBOL_3));
        add(new ItemStack(ItemInit.SYMBOL_4));
        add(new ItemStack(ItemInit.SYMBOL_5));
        add(new ItemStack(ItemInit.SYMBOL_6));
        add(new ItemStack(ItemInit.SYMBOL_7));
    }};

    public BlockSymbol(String name) {
        super(Material.ROCK, MapColor.GRAY);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setLightLevel(1.0F);
        this.setHardness(10);
        this.setResistance(10);
        doRegister();
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntitySymbol)
        {
            TileEntitySymbol te = (TileEntitySymbol) tileEntity;
            if (!te.isLock()) {
                te.change();
                if (!worldIn.isRemote && check(worldIn, pos)) {
                    playerIn.sendMessage(new TextComponentString("洞穴开始震动...阵法中发出不可理解的诡异光芒,它顷刻间将你包裹...你明白了一些晦涩难懂的东西..."));
                    ItemStack stack = new ItemStack(com.xwm.magicmaid.init.ItemInit.SKILL_BOOK);
                    ItemSkillBook.setSkill(stack, SkillRegistry.PERFORM_TAIJI);
                    EntityItem entityItem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    entityItem.setDefaultPickupDelay();
                    worldIn.spawnEntity(entityItem);
                    lock(worldIn, pos);
                }
                return true;
            }
            else
                return false;
        }
        else
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySymbol();
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }


    public static List<List<Pair<BlockPos, Item>>> MARK_POS = new ArrayList<List<Pair<BlockPos, Item>>>(){{

        // 阴阳
        add(Lists.newArrayList(
                Pair.of(new BlockPos(0, 1, 0).east(2), ItemInit.SYMBOL_BLACK) ,
                Pair.of(new BlockPos(0, 1, 0).west(2), ItemInit.SYMBOL_WHITE)
        ));

        // 四季
        add(Lists.newArrayList(
                Pair.of(new BlockPos(0, 2, 0).east(3).south(3), ItemInit.SYMBOL_SPRING),
                Pair.of(new BlockPos(0, 2, 0).west(3).south(3), ItemInit.SYMBOL_SUMMER),
                Pair.of(new BlockPos(0, 2, 0).west(3).north(3), ItemInit.SYMBOL_AUT),
                Pair.of(new BlockPos(0, 2, 0).east(3).north(3), ItemInit.SYMBOL_WINTER)
        ));

        // 五行
        add(Lists.newArrayList(
                Pair.of(new BlockPos(0, 3, 0).south(5), ItemInit.SYMBOL_GOLD),
                Pair.of(new BlockPos(0, 3, 0).west(5), ItemInit.SYMBOL_GREEN),
                Pair.of(new BlockPos(0, 3, 0).north(5).west(2), ItemInit.SYMBOL_BLUE),
                Pair.of(new BlockPos(0, 3, 0).north(5).east(2), ItemInit.SYMBOL_RED),
                Pair.of(new BlockPos(0, 3, 0).east(5), ItemInit.SYMBOL_EARTH)
        ));

        // 八卦
        add(Lists.newArrayList(
                Pair.of(new BlockPos(0, 4, 0).south(7), ItemInit.SYMBOL_0),
                Pair.of(new BlockPos(0, 4, 0).south(7).west(7), ItemInit.SYMBOL_1),
                Pair.of(new BlockPos(0, 4, 0).west(7), ItemInit.SYMBOL_2),
                Pair.of(new BlockPos(0, 4, 0).north(7).west(7), ItemInit.SYMBOL_3),
                Pair.of(new BlockPos(0, 4, 0).north(7), ItemInit.SYMBOL_4),
                Pair.of(new BlockPos(0, 4, 0).north(7).east(7), ItemInit.SYMBOL_5),
                Pair.of(new BlockPos(0, 4, 0).east(7), ItemInit.SYMBOL_6),
                Pair.of(new BlockPos(0, 4, 0).east(7).south(7), ItemInit.SYMBOL_7)
        ));
    }};

    public static boolean check(World world, BlockPos pos)
    {
        BlockPos corpsePos = null;
        for (int x = -10; x < 10; x++)
            for (int y = -5; y < 5; y++)
                for (int z = -10; z < 10; z++)
                {
                    BlockPos pos1 = pos.add(x, y, z);
                    if (world.getBlockState(pos1).getBlock() == BlockInit.BLOCK_CORPSE)
                    {
                        corpsePos = pos1;
                        break;
                    }
                }
        if (corpsePos == null)
            return false;

        for (List<Pair<BlockPos, Item>> posList : MARK_POS)
        {
            for (Pair<BlockPos, Item> pair : posList)
            {
                BlockPos pos1 = pair.getLeft();
                Item item = pair.getRight();
                TileEntity tileEntity = world.getTileEntity(corpsePos.add(pos1));
                if (tileEntity == null)
                    return false;

                if (!(tileEntity instanceof TileEntitySymbol))
                    return false;

                TileEntitySymbol symbol = (TileEntitySymbol) tileEntity;
                if (symbol.getSymbol().getItem() != item)
                    return false;
            }
        }


        return true;
    }

    public static void lock(World world, BlockPos pos)
    {
        BlockPos corpsePos = null;
        for (int x = -10; x < 10; x++)
            for (int y = -5; y < 5; y++)
                for (int z = -10; z < 10; z++)
                {
                    BlockPos pos1 = pos.add(x, y, z);
                    if (world.getBlockState(pos1).getBlock() == BlockInit.BLOCK_CORPSE)
                    {
                        corpsePos = pos1;
                        break;
                    }
                }
        if (corpsePos == null)
            return;

        for (List<Pair<BlockPos, Item>> posList : MARK_POS)
        {
            for (Pair<BlockPos, Item> pair : posList)
            {
                BlockPos pos1 = pair.getLeft();
                Item item = pair.getRight();
                TileEntity tileEntity = world.getTileEntity(corpsePos.add(pos1));
                if (tileEntity == null)
                    continue;

                if (!(tileEntity instanceof TileEntitySymbol))
                    continue;

                TileEntitySymbol symbol = (TileEntitySymbol) tileEntity;
                symbol.setLock(true);
                world.notifyBlockUpdate(pos1, world.getBlockState(pos1), world.getBlockState(pos1), 3);           }
        }
    }
}
