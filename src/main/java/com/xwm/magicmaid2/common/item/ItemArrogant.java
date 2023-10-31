package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemArrogant extends ItemBase {
    public ItemArrogant(String name) {
        super(name);
        this.setCreativeTab(null);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.YELLOW + "自命为神者，洞悉人道，凌驾众人之上，以人性驭人，然尚不足称神，空余傲慢");
        tooltip.add(TextFormatting.YELLOW + "在[荒芜之地]右键可以召唤涅瓦，同时也是帮助梅尔的物品");
    }
}
