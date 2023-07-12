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

public class ItemEvilIngot extends ItemBase
{
    public ItemEvilIngot(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.LIGHT_PURPLE + "融入邪恶的铁锭");
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
