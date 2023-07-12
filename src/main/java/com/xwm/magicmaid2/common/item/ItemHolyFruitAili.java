package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.entity.mob.maid.EntityMagicMaid;
import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidAili;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemHolyFruitAili extends ItemBase
{
    public ItemHolyFruitAili(String name) {
        super(name);
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
            return EnumActionResult.SUCCESS;
        else if (hand == EnumHand.MAIN_HAND){
            EntityMagicMaid maid;
            maid = new EntityMagicMaidAili(worldIn);
            maid.setPosition(pos.getX(), pos.getY()+1, pos.getZ());
            worldIn.spawnEntity(maid);
            player.getHeldItem(hand).shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "蒙尘而圣洁的物品，强大的魔法能量隐隐作显");
        tooltip.add(TextFormatting.YELLOW + "右键使用来召唤艾莉");
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
