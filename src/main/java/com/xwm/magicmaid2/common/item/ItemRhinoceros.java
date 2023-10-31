package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRhinoceros extends ItemBase {
    public ItemRhinoceros(String name) {
        super(name);
        this.setCreativeTab(null);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.YELLOW + "罕见的银色甲虫，旧世纪的人们将它当做护身符，向它祷告祈求庇护");
    }
}
