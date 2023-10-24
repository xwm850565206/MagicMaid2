package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid2.client.render.item.ItemEagerRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemIceWing extends ItemAnimatableWeapon
{
    public ItemIceWing(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels()
    {
        super.registerModels();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "众神的愤怒如玄冰般刺骨，涅瓦收集众神冰冷的愤怒，凝聚成破坏力极强的严寒");
        tooltip.add(TextFormatting.YELLOW + "玩家的使用效果还在制作中。。。");
    }
}