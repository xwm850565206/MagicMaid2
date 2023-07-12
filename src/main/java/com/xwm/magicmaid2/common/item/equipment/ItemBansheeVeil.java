package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.object.item.equipment.ItemArmor;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemBansheeVeil extends ItemArmor
{
    public ItemBansheeVeil(String name) {
        super(name);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "传闻卡修引动百里怨念化作纱幕，注入圣痕之中，做成面纱。");
        tooltip.add(TextFormatting.YELLOW + "心智不坚的人光是看着就会深陷怨念之中，化作怨灵。");
    }

}
