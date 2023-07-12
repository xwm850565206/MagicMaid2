package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.creativetab.CreativeTabMaid;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemEvilPickaxe extends ItemPickaxe implements IRegistrable, IHasModel
{
    public ItemEvilPickaxe(String name) {
        super(ToolMaterial.DIAMOND);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabMaid.CREATIVE_TAB_MAID);
        doRegister();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.LIGHT_PURPLE + "邪恶锭制作而成的稿子,拥有更高的收获等级,是收集无垢之身的工具");
    }


    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, @javax.annotation.Nullable net.minecraft.entity.player.EntityPlayer player, @javax.annotation.Nullable IBlockState blockState)
    {
       return 4;
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
