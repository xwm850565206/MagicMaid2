package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.Main;
import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid.util.Reference;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMemoryCorpse extends ItemBase
{
    public ItemMemoryCorpse(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.LIGHT_PURPLE + "一段残骸的记忆信息");
        tooltip.add(TextFormatting.YELLOW + "可以右键查看记忆，同时可以强化魔法石板");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (handIn == EnumHand.MAIN_HAND){
            playerIn.openGui(Main.instance, Reference.GUI_MAID_MEMORY, worldIn, 6, 0,0 );
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        else
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
