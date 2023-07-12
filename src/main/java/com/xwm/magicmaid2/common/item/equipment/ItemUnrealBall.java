package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid2.client.render.item.ItemAnimatableArmorRenderer;
import com.xwm.magicmaid2.client.render.item.ItemUnrealBallRenderer;
import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableWeapon;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemUnrealBall extends ItemAnimatableWeapon
{
    public ItemUnrealBall(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels()
    {
        super.registerModels();
        this.setTileEntityItemStackRenderer(new ItemUnrealBallRenderer());
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "狐族至宝，可以让一切生灵陷入梦境。");
        tooltip.add(TextFormatting.YELLOW + "玩家的使用效果还在制作中。。。");
    }
}