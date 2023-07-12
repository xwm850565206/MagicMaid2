package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.Main;
import com.xwm.magicmaid.creativetab.CreativeTabMaid;
import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemSageSoup extends ItemAppleGold implements IHasModel, IRegistrable {

    public ItemSageSoup(String name) {
        super(6, 1.2F, true);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHasSubtypes(false);
        this.setCreativeTab(CreativeTabMaid.CREATIVE_TAB_MAID);
        this.doRegister();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "贤者之骨磨成的粉末，可以食用，效果与金苹果相当");
    }

    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.COMMON;
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
