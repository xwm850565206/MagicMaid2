package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid2.client.render.item.ItemEagerRenderer;
import com.xwm.magicmaid2.client.render.item.ItemUnrealBallRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemEager extends ItemAnimatableWeapon
{
    public ItemEager(String name) {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels()
    {
        super.registerModels();
        this.setTileEntityItemStackRenderer(new ItemEagerRenderer());
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "心猿难束，意马难栓，深居天堂的众神亦有欲念。");
        tooltip.add(TextFormatting.YELLOW + "汐墨以贤者头骨为皿，收集各种欲念炼制而成，普通人光是靠近就会欲念爆发而疯");
        tooltip.add(TextFormatting.YELLOW + "玩家的使用效果还在制作中。。。");
    }
}