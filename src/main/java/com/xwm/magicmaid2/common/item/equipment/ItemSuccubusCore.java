package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.object.item.equipment.ItemArmor;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemSuccubusCore extends ItemArmor
{
    public ItemSuccubusCore(String name) {
        super(name);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "汐墨从欲念中诞生，享用世间大量欲念，其中极其纯粹者，被聚合成晶，形成欲之核");
    }
}
