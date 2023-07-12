package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.object.item.equipment.ItemArmor;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemFoxArmor extends ItemArmor
{
    public ItemFoxArmor(String name) {
        super(name);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "狐族的传承之宝，由天神缪莎所制，据说穿戴者可以不受一切外力侵害。");
    }
}
